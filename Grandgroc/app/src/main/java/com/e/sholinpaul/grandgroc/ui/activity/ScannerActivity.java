package com.e.sholinpaul.grandgroc.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.CheckAssignedOrderListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.QRCodeFoundListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.OrdersCloudManager;
import com.e.sholinpaul.grandgroc.databinding.ActivityScannerBinding;
import com.e.sholinpaul.grandgroc.model.Model.AllOrderModel;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.model.Model.PlaceModel;
import com.e.sholinpaul.grandgroc.model.Model.ProductModel;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ScannerActivity extends BaseActivity implements CheckAssignedOrderListener {
    ActivityScannerBinding binding;
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    String accessToken;
    String deviceId;
    ProductModel productModel;
    ArrayList<OrderModel> orderData;
    AllOrderModel allOrderModel;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    int page = 1;

    private String qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScannerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
    }

    private void init() {

        binding.activityMainQrCodeFoundButton.setVisibility(View.INVISIBLE);
        binding.activityMainQrCodeFoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qrCode.isEmpty()) {
                    showMessage("Data not found");
                } else {
                    fetchCheckAssignedOrder(Integer.parseInt(qrCode));

                    Log.i(ScannerActivity.class.getSimpleName(), "Data Found: " + qrCode);
                }


            }
        });

        binding.btnCloseScanner.setOnClickListener(view -> {
            finish();
        });

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        requestCamera();


    }


    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(ScannerActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Error starting camera " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
        binding.activityMainPreviewView.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(binding.activityMainPreviewView.createSurfaceProvider());

        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new QRCodeImageAnalyzer(new QRCodeFoundListener() {
            @Override
            public void onQRCodeFound(String _qrCode) {
                qrCode = _qrCode;
//                StringTokenizer tokens = new StringTokenizer(_qrCode, "|");
//
//                //////////////Order id///////////
//                String first = tokens.nextToken();
//
//                StringTokenizer firstData = new StringTokenizer(first, "-");
//                String firstData1 = firstData.nextToken();
//                String firstData2 = firstData.nextToken();
//
//                convertedOrderId = Integer.parseInt(firstData2);

//                ////////////store id//////////
//                String second = tokens.nextToken();
//
//                StringTokenizer secondData = new StringTokenizer(second, "-");
//                String secondData1 = secondData.nextToken();
//                String secondData2 = secondData.nextToken();
//
//                convertedStoreId = Integer.parseInt(secondData2);
                binding.activityMainQrCodeFoundButton.setVisibility(View.VISIBLE);


            }

            @Override
            public void qrCodeNotFound() {
                binding.activityMainQrCodeFoundButton.setVisibility(View.INVISIBLE);
            }
        }));

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis, preview);
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
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}