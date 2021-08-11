package com.e.sholinpaul.grandgroc.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.sholinpaul.grandgroc.R;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.NewOrderListListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.OrdersCloudManager;
import com.e.sholinpaul.grandgroc.model.Model.AllOrderModel;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.ui.fragmentAdapter.CurrentOrderFragmentAdapter;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CurrentOrderFragment extends BaseFragments implements NewOrderListListener {
    View view;
    String accessToken;
    String deviceId;
    CurrentOrderFragment fragment;
    CurrentOrderFragmentAdapter adapter;
    ArrayList<OrderModel> orderData;
    AllOrderModel allOrderModel;
    RecyclerView rvCurrentOrder;
    RelativeLayout schListNotFound, rlCMain;
    LinearLayout lLoading;
    int page = 1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.current_order_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        orderData = new ArrayList<>();
        rvCurrentOrder = view.findViewById(R.id.rvCurrentOrder);
        schListNotFound = view.findViewById(R.id.schListNotFound);
        rlCMain = view.findViewById(R.id.rlCMain);
        lLoading = view.findViewById(R.id.lLoading);
        fetchCurrentOrderFromServer(page);
        rvCurrentOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private void fetchCurrentOrderFromServer(int page) {
        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();
        OrdersCloudManager orderListCloudManager = new OrdersCloudManager(getActivity());
        orderListCloudManager.fetchNewOrders(accessToken, deviceId, "collected", page, this);

        for (int i = 0; i < rlCMain.getChildCount(); i++) {
            View view = rlCMain.getChildAt(i);
            enableDisableView(view, false);
        }
        lLoading.setVisibility(View.VISIBLE);

    }

    private void setDataToAdapter(final ArrayList<OrderModel> data) {

        if (data.size() > 0) {
            if (getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    adapter = new CurrentOrderFragmentAdapter(data, getActivity(), fragment);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                    rvCurrentOrder.setLayoutManager(gridLayoutManager);
                    rvCurrentOrder.setAdapter(adapter);


                }
            });

            schListNotFound.setVisibility(View.GONE);
        } else {
            schListNotFound.setVisibility(View.VISIBLE);
            lLoading.setVisibility(View.GONE);
        }

    }



    @Override
    public void onResume() {
        super.onResume();
        fetchCurrentOrderFromServer(page);
    }

    @Override
    public void fetchNewOrder(AllOrderModel Assigned_orders) {
        orderData = Assigned_orders.getData();
        allOrderModel = Assigned_orders;
        page = Assigned_orders.getCurrent_page();

        if (page == 1) {
            setDataToAdapter(orderData);
        } else {
            adapter.addAll(orderData);
            adapter.notifyDataSetChanged();
        }
        for (int i = 0; i < rlCMain.getChildCount(); i++) {
            View view = rlCMain.getChildAt(i);
            enableDisableView(view, true);
        }
        lLoading.setVisibility(View.GONE);
    }

    private void attachMoreData() {
        page++;
        fetchCurrentOrderFromServer(page);
    }

    @Override
    public void fetchNewOrderListFailed(String errorMessage) {
        for (int i = 0; i < rlCMain.getChildCount(); i++) {
            View view = rlCMain.getChildAt(i);
            enableDisableView(view, true);
        }
        lLoading.setVisibility(View.GONE);

    }


    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }
}