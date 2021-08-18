package com.e.sholinpaul.grandgroc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.CheckAssignedOrderListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.OrdersCloudManager;
import com.e.sholinpaul.grandgroc.databinding.ActivityScannerBinding;
import com.e.sholinpaul.grandgroc.model.Model.AllOrderModel;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.model.Model.PlaceModel;
import com.e.sholinpaul.grandgroc.model.Model.ProductModel;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class ScannerActivity extends BaseActivity implements CheckAssignedOrderListener {
    ActivityScannerBinding binding;
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    String accessToken;
    String deviceId;
    ProductModel productModel;
    private String qrCode;

    ArrayList<OrderModel> orderData;
    AllOrderModel allOrderModel;
    int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScannerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();

    }

    private void init() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(ScannerActivity.this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                qrCode = intentResult.getContents();
                fetchCheckAssignedOrder(Integer.parseInt(qrCode));

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void fetchCheckAssignedOrder(int Order_id) {
        accessToken = BusinessDetailsGenerator.getInstance(this).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(this).getDeviceId();
        OrdersCloudManager orderListCloudManager = new OrdersCloudManager(this);
        orderListCloudManager.CheckAssignedOrder(deviceId, accessToken, Order_id, this);
    }


    @Override
    public void fetchCheckedAssignedOrderDetails(PlaceModel order, List<ProductModel> order_details, OrderModel AssignedOrder) {

        for (int i = 0; i < order_details.size(); i++) {
            productModel = order_details.get(i);
        }

        String OrderID = String.valueOf(productModel.getOrder_id());
        String ID = String.valueOf(AssignedOrder.getId());
        Toast.makeText(this, ID, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ScannerActivity.this, OrderDetailsActivity.class);
        intent.putExtra("ORDER_ID", OrderID);
        intent.putExtra("ID", ID);
        intent.putExtra("ActivityState", "scanActivity");
        startActivityForResult(intent, 301);
    }

    @Override
    public void fetchCheckAssignedOrderDetailsFailed(String errorMessage) {
        Toast.makeText(this, "Qr Code is invalid", Toast.LENGTH_SHORT).show();
    }
}