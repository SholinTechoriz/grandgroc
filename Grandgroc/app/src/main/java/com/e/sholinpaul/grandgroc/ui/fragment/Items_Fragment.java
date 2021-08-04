package com.e.sholinpaul.grandgroc.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.OrderDetailsListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.OrdersCloudManager;
import com.e.sholinpaul.grandgroc.databinding.ItemsFragmentBinding;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.model.Model.PlaceModel;
import com.e.sholinpaul.grandgroc.model.Model.ProductModel;
import com.e.sholinpaul.grandgroc.ui.fragmentAdapter.ItemListFragmentAdapter;
import com.e.sholinpaul.grandgroc.ui.fragmentAdapter.PriceListFragmentAdapter;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;

import java.util.List;

public class Items_Fragment extends Fragment implements OrderDetailsListener {
    View view;
    ItemsFragmentBinding binding;
    int Order_id, id;
    String accessToken, deviceId;
    ItemListFragmentAdapter adapter;
    PriceListFragmentAdapter adapter1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ItemsFragmentBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        init();
        return view;

    }

    private void init() {
        final OrderModel orderModel = getArguments().getParcelable("order");
//        final String ScanState = getArguments().getString("ActivityState");
        final String AllOrdersState = getArguments().getString("OrderLIST");

// 

            if (AllOrdersState.equals("OrderListActivity")) {
            Toast.makeText(getActivity(), " from OrderList Activity", Toast.LENGTH_SHORT).show();

            Order_id = orderModel.getOrder_id();
            id = orderModel.getId();

            fetchAllOrderList();
        }

    }

    private void fetchAllOrderList() {

        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();
        OrdersCloudManager ordersCloudManager = new OrdersCloudManager(getActivity());
        ordersCloudManager.fetchAllOrdersList(deviceId, accessToken, id, Order_id, this);
        binding.lLoading.setVisibility(View.VISIBLE);

    }

    private void setDataToAdapter(final List<ProductModel> data) {

        if (data.size() > 0) {
            if (getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    adapter = new ItemListFragmentAdapter(data, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    binding.rvItemList.setLayoutManager(linearLayoutManager);
                    binding.rvItemList.setAdapter(adapter);


                }
            });

            binding.schItemListNotFound.setVisibility(View.GONE);
        } else {
            binding.schItemListNotFound.setVisibility(View.VISIBLE);
            binding.lLoading.setVisibility(View.GONE);
        }

    }

    private void setDataToAdapter1(final List<ProductModel> data) {

        if (data.size() > 0) {
            if (getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    adapter1 = new PriceListFragmentAdapter(data, getActivity());
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    binding.rvPriceList.setLayoutManager(linearLayoutManager1);
                    binding.rvPriceList.setAdapter(adapter1);


                }
            });

            binding.schPriceListNotFound.setVisibility(View.GONE);
        } else {
            binding.schPriceListNotFound.setVisibility(View.VISIBLE);
            binding.lLoading.setVisibility(View.GONE);
        }

    }


    @Override
    public void fetchOrderDetails(OrderModel AssignedOrder, PlaceModel order, List<ProductModel> order_details) {
        binding.lLoading.setVisibility(View.GONE);

        setDataToAdapter(order_details);
        setDataToAdapter1(order_details);

    }

    @Override
    public void fetchOrderDetailsFailed(String errorMessage) {
        binding.lLoading.setVisibility(View.GONE);

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onConnectionFailure(int errorCode) {

    }
}