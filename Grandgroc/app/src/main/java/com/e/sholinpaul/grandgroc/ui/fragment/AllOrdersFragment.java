package com.e.sholinpaul.grandgroc.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.sholinpaul.grandgroc.R;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.GetOrderTypeListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.NewOrderListListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.OrdersCloudManager;
import com.e.sholinpaul.grandgroc.databinding.FragmentAllOrdersBinding;
import com.e.sholinpaul.grandgroc.model.Model.AllOrderModel;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.ui.fragmentAdapter.AllOrderFragmentAdapter;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class AllOrdersFragment extends BaseFragments implements NewOrderListListener, GetOrderTypeListener {

    private FragmentAllOrdersBinding binding;
    AllOrdersFragment fragment;
    String accessToken;
    String deviceId;
    String spinnerData;
    int page;
    List<OrderModel> OrderTypesModel;
    AllOrderModel allOrderModel;
    ArrayList<OrderModel> orderData;
    AllOrderFragmentAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();
        return root;
    }

    private void init() {

        fetchOrderTypes();

        binding.spOrdersType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerData = parent.getItemAtPosition(position).toString();
                fetchNewOrderFromServer(1, spinnerData);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.rvAllOrders.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(0) && newState == RecyclerView.SCROLL_INDICATOR_BOTTOM) {
                    if (page != allOrderModel.getLast_page() && allOrderModel.getNext_page_url() != null) {

                        attachMoreData();
                    } else {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.last_page), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void setDataToAdapter(final ArrayList<OrderModel> data) {

        if (data.size() > 0) {
            if (getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    adapter = new AllOrderFragmentAdapter(data, getActivity(), fragment);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                    binding.rvAllOrders.setLayoutManager(gridLayoutManager);
                    binding.rvAllOrders.setAdapter(adapter);

                }
            });

            binding.schListNotFound.setVisibility(View.GONE);
        } else {
            binding.schListNotFound.setVisibility(View.VISIBLE);
            binding.lLoading.setVisibility(View.GONE);
        }

    }


    private void fetchNewOrderFromServer(int page, String OType) {
        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();
        OrdersCloudManager orderListCloudManager = new OrdersCloudManager(getActivity());
        orderListCloudManager.fetchNewOrders(accessToken, deviceId, OType, page, this);
        binding.lLoading.setVisibility(View.VISIBLE);
    }


    private void fetchOrderTypes() {
        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();
        OrdersCloudManager orderListCloudManager = new OrdersCloudManager(getActivity());
        orderListCloudManager.fetchOrderTypes(deviceId, accessToken, this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void fetchNewOrder(AllOrderModel Assigned_orders) {
        allOrderModel = Assigned_orders;
        orderData = Assigned_orders.getData();

        page = Assigned_orders.getCurrent_page();

        if (page == 1) {
            setDataToAdapter(orderData);
        } else {
            adapter.addAll(orderData);
            adapter.notifyDataSetChanged();
        }

        binding.lLoading.setVisibility(View.GONE);
    }


    private void attachMoreData() {
        page++;
        fetchNewOrderFromServer(page, "");
    }

    @Override
    public void fetchNewOrderListFailed(String errorMessage) {
        binding.lLoading.setVisibility(View.GONE);

    }


    @Override
    public void fetchOrderTypes(String message, List<OrderModel> deliverytypes) {
        OrderTypesModel = new ArrayList<>();
        OrderTypesModel.addAll(deliverytypes);
        ArrayList<String> categoryNames = new ArrayList<>();
//        categoryNames.add("All");

        for (int i = 0; i < deliverytypes.size(); i++) {
            categoryNames.add(deliverytypes.get(i).getType());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, categoryNames);
        binding.spOrdersType.setAdapter(adapter);

    }

    @Override
    public void fetchOrderTypesFailed(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}