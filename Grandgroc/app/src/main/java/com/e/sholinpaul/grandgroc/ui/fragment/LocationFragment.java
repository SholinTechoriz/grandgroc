package com.e.sholinpaul.grandgroc.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.e.sholinpaul.grandgroc.R;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.OrderDetailsListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.PostStatusChangeListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.OrdersCloudManager;
import com.e.sholinpaul.grandgroc.databinding.LocationFragmentBinding;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.model.Model.PlaceModel;
import com.e.sholinpaul.grandgroc.model.Model.ProductModel;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;

import java.util.List;

public class LocationFragment extends Fragment implements OrderDetailsListener, PostStatusChangeListener {
    View view;
    int Order_id, id;
    String accessToken, deviceId, status;
    LocationFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = LocationFragmentBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        init();
        return view;
    }

    private void init() {
        final OrderModel orderModel = getArguments().getParcelable("order");
        final String ScanState = getArguments().getString("ActivityState");
        final String AllOrdersState = getArguments().getString("OrderLIST");

        if (ScanState != "scanActivity") {
            final String OId = getArguments().getString("orderscanned");
            Order_id = Integer.parseInt(OId);
            id = orderModel.getId();
            Log.d("qwerty", " Order id 1 : " + Order_id);

        } else if (AllOrdersState.equals("OrderListActivity")) {

            Order_id = orderModel.getOrder_id();
            id = orderModel.getId();
            Log.d("qwerty", " Order id 2 : " + Order_id);

        }


        fetchAllOrderList();

        binding.btnCollected.setOnClickListener(v -> {

            status = "collected";
            if (ScanState.equals("ScanState")) {
                postChangesByScanner();
            } else {
                postChangeStatus();

            }
        });

        binding.btnDelivered.setOnClickListener(v -> {
            status = "delivered";
            if (ScanState.equals("ScanState")) {
                postChangesByScanner();
            } else {
                postChangeStatus();

            }
        });

        binding.btnCompleted.setOnClickListener(v -> {

            status = "completed";
            if (ScanState.equals("ScanState")) {
                postChangesByScanner();
            } else {
                postChangeStatus();

            }
        });
    }

    private void fetchAllOrderList() {

        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();
        OrdersCloudManager ordersCloudManager = new OrdersCloudManager(getActivity());
        ordersCloudManager.fetchAllOrdersList(deviceId, accessToken, id, Order_id, this);
//        binding.lLoading.setVisibility(View.VISIBLE);

    }


    private void postChangesByScanner() {
        Toast.makeText(getActivity(), "scanner", Toast.LENGTH_SHORT).show();
        OrderModel orderModel = new OrderModel();
        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();

        orderModel.setDevice_id(deviceId);
        orderModel.setApi_token(accessToken);
        orderModel.setStatus(status);
        orderModel.setAssignordersID(id);
        OrdersCloudManager ordersCloudManager = new OrdersCloudManager(getActivity());
        ordersCloudManager.PostStatusByScanner(orderModel, this);

    }

    private void postChangeStatus() {
        Toast.makeText(getActivity(), "Activity", Toast.LENGTH_SHORT).show();

        OrderModel orderModel = new OrderModel();
        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();

        orderModel.setDevice_id(deviceId);
        orderModel.setApi_token(accessToken);
        orderModel.setStatus(status);
        orderModel.setAssignordersID(id);
        OrdersCloudManager ordersCloudManager = new OrdersCloudManager(getActivity());
        ordersCloudManager.PostChangeStatusBy(orderModel, this);

    }


    private void setDataToTextview(PlaceModel placeModel) {

        binding.tvName.setText(placeModel.getName());
        binding.tvHomeAddress.setText(placeModel.getLocation());
        binding.tvLocation1.setText(placeModel.getTown() + " , " + placeModel.getPin());
        binding.tvDistrict.setText(placeModel.getState_name() + " , " + placeModel.getCountry_name());
        binding.tvLandmark.setText(placeModel.getLandmark());
        binding.tvPhone.setText(placeModel.getPhone());


    }

    @Override
    public void fetchOrderDetails(OrderModel AssignedOrder, PlaceModel order, List<ProductModel> order_details) {

        Order_id = AssignedOrder.getOrder_id();
        id = AssignedOrder.getId();

        Log.d("qwertyuiop", "size  Location ddd  :" + order_details.size());

        status = AssignedOrder.getStatus();
        setDataToTextview(order);


        if (status.equals("collected")) {
            binding.btnCollected.setBackgroundColor(getResources().getColor(R.color.colorAlphaRed));
            binding.btnCollected.setTextColor(getResources().getColor(R.color.colorWhite));

            binding.btnCompleted.setBackgroundColor(getResources().getColor(R.color.colorUltraLightGreen));
            binding.btnCompleted.setTextColor(getResources().getColor(R.color.colorBlack));
            binding.btnDelivered.setBackgroundColor(getResources().getColor(R.color.colorUltraLightGreen));
            binding.btnDelivered.setTextColor(getResources().getColor(R.color.colorBlack));

        } else if (status.equals("completed")) {
            binding.btnCompleted.setBackgroundColor(getResources().getColor(R.color.colorAlphaRed));
            binding.btnCompleted.setTextColor(getResources().getColor(R.color.colorWhite));

            binding.btnCollected.setBackgroundColor(getResources().getColor(R.color.colorUltraLightGreen));
            binding.btnCollected.setTextColor(getResources().getColor(R.color.colorBlack));

            binding.btnDelivered.setBackgroundColor(getResources().getColor(R.color.colorUltraLightGreen));
            binding.btnDelivered.setTextColor(getResources().getColor(R.color.colorBlack));

        } else if (status.equals("delivered")) {
            binding.btnDelivered.setBackgroundColor(getResources().getColor(R.color.colorAlphaRed));
            binding.btnDelivered.setTextColor(getResources().getColor(R.color.colorWhite));

            binding.btnCompleted.setBackgroundColor(getResources().getColor(R.color.colorUltraLightGreen));
            binding.btnCompleted.setTextColor(getResources().getColor(R.color.colorBlack));
            binding.btnCollected.setBackgroundColor(getResources().getColor(R.color.colorUltraLightGreen));
            binding.btnCollected.setTextColor(getResources().getColor(R.color.colorBlack));

        }


    }

    @Override
    public void fetchOrderDetailsFailed(String errorMessage) {

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

    @Override
    public void onResume() {
        super.onResume();
        fetchAllOrderList();
    }

    @Override
    public void postChangeStatus(OrderModel data, String Message) {
        Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void postChangeStatusFailed(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();

    }
}