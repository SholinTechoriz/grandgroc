package com.e.sholinpaul.grandgroc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.e.sholinpaul.grandgroc.R;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.CheckAssignedOrderListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.OrdersCloudManager;
import com.e.sholinpaul.grandgroc.databinding.ActivityDashboardBinding;
import com.e.sholinpaul.grandgroc.databinding.SinglelogoutlayoutBinding;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.model.Model.PlaceModel;
import com.e.sholinpaul.grandgroc.model.Model.ProductModel;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class DashboardActivity extends BaseActivity implements CheckAssignedOrderListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashboardBinding binding;
    String accessToken;
    String deviceId;
    ProductModel productModel;
    private String qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_viewOrder)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu);

        View header = navigationView.inflateHeaderView(R.layout.nav_header_dashboard);
        TextView ProfName = header.findViewById(R.id.tvHProfileName);
        ProfName.setText(BusinessDetailsGenerator.getInstance(this).getName());
        TextView loc = header.findViewById(R.id.tvHPlace);
        loc.setText(BusinessDetailsGenerator.getInstance(this).getLocation());


        ImageView headerImg = header.findViewById(R.id.ivHeaderImg);
        String realpath = ("http://projects.techoriz.in/grandgroc/public/assets/uploads/delivaryboys/" + BusinessDetailsGenerator.getInstance(this).getProfilePic());

        Log.d("imgpath", "real Path is : " + realpath);
        Glide.with(this)
                .load(realpath)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_loading).error(R.drawable.ic_profile)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(headerImg);

        Menu menu = navigationView.getMenu();
        MenuItem nav_logout = menu.findItem(R.id.nav_logout);
        nav_logout.setOnMenuItemClickListener(menuItem -> {
            Logout();
            return false;
        });

    }

    public void Logout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final SinglelogoutlayoutBinding layoutBinding = SinglelogoutlayoutBinding.inflate(getLayoutInflater());
        View view = layoutBinding.getRoot();
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        assert dialog.getWindow() != null;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 20);
        dialog.getWindow().setBackgroundDrawable(inset);
        dialog.show();
        layoutBinding.btnYes.setOnClickListener(view1 -> {

            SharedPreferences preferences = getSharedPreferences("MyLoyalitySharedPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            Intent logoutIntent = new Intent(DashboardActivity.this, SplashActivity.class);
            startActivity(logoutIntent);
            finish();
            dialog.dismiss();
        });
        layoutBinding.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                Toast.makeText(this, "Not yet assigned", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_scan:
//                Intent intent = new Intent(DashboardActivity.this, ScannerActivity.class);
//                startActivity(intent);
                functionScanner();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void functionScanner() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(DashboardActivity.this);
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
                if (intentResult.getFormatName() != ("QR_CODE")) {
                    showMessage("Invalid Data");
                } else {
                    fetchCheckAssignedOrder(Integer.parseInt(qrCode));
                }


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
        Intent intent = new Intent(DashboardActivity.this, OrderDetailsActivity.class);
        intent.putExtra("ORDER_ID", OrderID);
        intent.putExtra("ID", ID);
        intent.putExtra("ActivityState", "scanActivity");
        startActivityForResult(intent, 301);
    }

    @Override
    public void fetchCheckAssignedOrderDetailsFailed(String errorMessage) {
        Toast.makeText(this, "Qr Code is invalid", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}