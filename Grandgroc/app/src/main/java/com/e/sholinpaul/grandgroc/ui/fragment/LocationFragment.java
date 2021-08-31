package com.e.sholinpaul.grandgroc.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.e.sholinpaul.grandgroc.R;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.OrderDetailsListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.PostStatusChangeListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.OrdersCloudManager;
import com.e.sholinpaul.grandgroc.databinding.LocationFragmentBinding;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.model.Model.PlaceModel;
import com.e.sholinpaul.grandgroc.model.Model.ProductModel;
import com.e.sholinpaul.grandgroc.ui.fragmentAdapter.CurrentOrderFragmentAdapter;
import com.e.sholinpaul.grandgroc.ui.fragmentAdapter.NewOrderFragmentAdapter;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class LocationFragment extends BaseFragments implements OrderDetailsListener, PostStatusChangeListener, OnMapReadyCallback {
    View view;
    int Order_id, id;
    String accessToken, deviceId, status;
    LocationFragmentBinding binding;
    CurrentOrderFragmentAdapter cAdapter;
    NewOrderFragmentAdapter nAdapter;
    GoogleMap map;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = LocationFragmentBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        init();
        return view;
    }

    private void init() {

        BuildMap();

        assert getArguments() != null;
        final OrderModel orderModel = getArguments().getParcelable("order");
        final String ScanState = getArguments().getString("ActivityState");
        final String AllOrdersState = getArguments().getString("OrderLIST");


        if (ScanState.equals("scanActivity")) {
            final String OId = getArguments().getString("orderscanned");
            final String ID = getArguments().getString("ID");

            Order_id = Integer.parseInt(OId);
            id = Integer.parseInt(ID);

            fetchAllOrderList();

        } else if (AllOrdersState.equals("OrderListActivity")) {

            if (orderModel.equals("")) {
                Toast.makeText(getActivity(), "empty Data", Toast.LENGTH_SHORT).show();
            } else {
                Order_id = orderModel.getOrder_id();
                id = orderModel.getId();
            }
            fetchAllOrderList();

        }


        binding.btnCollected.setOnClickListener(v -> {
            if (doubleClickPrevent()) {
                status = "collected";
                if (ScanState.equals("ScanState")) {
                    postChangesByScanner();
                } else {
                    postChangeStatus();

                }
            }

        });

        binding.btnDelivered.setOnClickListener(v -> {
            if (doubleClickPrevent()) {
                status = "delivered";
                if (ScanState.equals("ScanState")) {
                    postChangesByScanner();
                } else {
                    postChangeStatus();

                }
            }

        });

        binding.btnCompleted.setOnClickListener(v -> {
            if (doubleClickPrevent()) {
                status = "completed";
                if (ScanState.equals("ScanState")) {
                    postChangesByScanner();
                } else {
                    postChangeStatus();

                }
            }
        });
    }

    private void BuildMap() {
        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


    }

    private void fetchAllOrderList() {

        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();
        OrdersCloudManager ordersCloudManager = new OrdersCloudManager(getActivity());
        ordersCloudManager.fetchAllOrdersList(deviceId, accessToken, id, Order_id, this);

        for (int i = 0; i < binding.rlLMain.getChildCount(); i++) {
            View view = binding.rlLMain.getChildAt(i);
            enableDisableView(view, false);
        }
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
        
        OrderModel orderModel = new OrderModel();
        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();

        orderModel.setDevice_id(deviceId);
        orderModel.setApi_token(accessToken);
        orderModel.setStatus(status);
        orderModel.setAssignordersID(id);
        OrdersCloudManager ordersCloudManager = new OrdersCloudManager(getActivity());
        ordersCloudManager.PostChangeStatusBy(orderModel, this);

        for (int i = 0; i < binding.rlLMain.getChildCount(); i++) {
            View view = binding.rlLMain.getChildAt(i);
            enableDisableView(view, false);
        }

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
        status = AssignedOrder.getStatus();

        setDataToTextview(order);


//        Drawable drawable = getResources().getDrawable(R.drawable.ic_green_tick);

        switch (status) {

            case "delivered":
//                binding.btnDelivered.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                binding.btnDelivered.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.btnDelivered.setTextColor(getResources().getColor(R.color.colorPrimary));

                binding.btnCompleted.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCompleted.setTextColor(getResources().getColor(R.color.colorWhite));

                binding.btnCollected.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCollected.setTextColor(getResources().getColor(R.color.colorWhite));
                break;


            case "collected":

//                binding.btnCollected.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                binding.btnCollected.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.btnCollected.setTextColor(getResources().getColor(R.color.colorPrimary));

                binding.btnCompleted.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCompleted.setTextColor(getResources().getColor(R.color.colorWhite));

                binding.btnDelivered.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnDelivered.setTextColor(getResources().getColor(R.color.colorWhite));
                break;


            case "completed":
//                binding.btnCompleted.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                binding.btnCompleted.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.btnCompleted.setTextColor(getResources().getColor(R.color.colorPrimary));

                binding.btnCollected.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCollected.setTextColor(getResources().getColor(R.color.colorWhite));

                binding.btnDelivered.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnDelivered.setTextColor(getResources().getColor(R.color.colorWhite));
                break;

            default:

                binding.btnCompleted.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCompleted.setTextColor(getResources().getColor(R.color.colorWhite));


                binding.btnCollected.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCollected.setTextColor(getResources().getColor(R.color.colorWhite));


                binding.btnDelivered.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnDelivered.setTextColor(getResources().getColor(R.color.colorWhite));

        }


        for (int i = 0; i < binding.rlLMain.getChildCount(); i++) {
            View view = binding.rlLMain.getChildAt(i);
            enableDisableView(view, true);
        }
    }

    @Override
    public void fetchOrderDetailsFailed(String errorMessage) {

        for (int i = 0; i < binding.rlLMain.getChildCount(); i++) {
            View view = binding.rlLMain.getChildAt(i);
            enableDisableView(view, true);
        }
    }


    @Override
    public void postChangeStatus(OrderModel data, String Message) {
        getActivity().setResult(301);
        Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < binding.rlLMain.getChildCount(); i++) {
            View view = binding.rlLMain.getChildAt(i);
            enableDisableView(view, true);
        }
        fetchAllOrderList();

    }

    @Override
    public void postChangeStatusFailed(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < binding.rlLMain.getChildCount(); i++) {
            View view = binding.rlLMain.getChildAt(i);
            enableDisableView(view, true);
        }
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng Thodupuzha = new LatLng(9.892980, 76.722107);
        map.addMarker(new MarkerOptions().position(Thodupuzha).title("Thodupuzha"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Thodupuzha, 15f));
        map.getUiSettings().setZoomControlsEnabled(true);
    }


}