package com.e.sholinpaul.grandgroc.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.OrderDetailsListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.OrdersCloudManager;
import com.e.sholinpaul.grandgroc.databinding.LocationFragmentBinding;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.model.Model.PlaceModel;
import com.e.sholinpaul.grandgroc.model.Model.ProductModel;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;

import java.util.List;

public class LocationFragment extends Fragment implements OrderDetailsListener {
    View view;
    int Order_id, id;
    String accessToken, deviceId;
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
        Order_id = orderModel.getOrder_id();
        id = orderModel.getId();
        fetchAllOrderList();
    }

    private void fetchAllOrderList() {

        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();
        OrdersCloudManager ordersCloudManager = new OrdersCloudManager(getActivity());
        ordersCloudManager.fetchAllOrdersList(deviceId, accessToken, id, Order_id, this);
//        binding.lLoading.setVisibility(View.VISIBLE);

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
        setDataToTextview(order);

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
}