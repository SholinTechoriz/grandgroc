package com.e.sholinpaul.grandgroc.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.e.sholinpaul.grandgroc.R;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.NewOrderListListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.OrdersCloudManager;
import com.e.sholinpaul.grandgroc.databinding.ActivityOrderDetailsBinding;
import com.e.sholinpaul.grandgroc.model.Model.AllOrderModel;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.ui.fragment.Items_Fragment;
import com.e.sholinpaul.grandgroc.ui.fragment.LocationFragment;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class OrderDetailsActivity extends BaseActivity implements NewOrderListListener {
    ActivityOrderDetailsBinding binding;
    OrderModel orderModel;
    String order_Id;
    int page = 1;
    String oID;
    String Id;
    ArrayList<OrderModel> orderData;

    ArrayList<Fragment> mFragmentArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
    }

    private void init() {
        fetchNewOrderFromServer(page);
        setupViewPager();
        CustomTabView();
    }


    public void CustomTabView() {

        binding.tabs1.getTabAt(0).setCustomView(R.layout.tab_neworder);
        TextView tView11 = (TextView) binding.tabs1.getTabAt(0).getCustomView().findViewById(R.id.tv_tabName1);
        tView11.setText("ITEMS");

        binding.tabs1.getTabAt(1).setCustomView(R.layout.tab_currentorder);
        TextView tView1 = (TextView) binding.tabs1.getTabAt(1).getCustomView().findViewById(R.id.tv_tabName2);
        tView1.setText("LOCATION");

    }


    private void setupViewPager() {

        orderModel = getIntent().getParcelableExtra("order");

        String scanState = getIntent().hasExtra("ActivityState") ? getIntent().getStringExtra("ActivityState") : "";

        String OrderListState = getIntent().hasExtra("OrderLIST") ? getIntent().getStringExtra("OrderLIST") : "";


        if (scanState.equals("scanActivity")) {
            order_Id = getIntent().getStringExtra("ORDER_ID");
            Id = getIntent().getStringExtra("ID");
            toolbarSection("#OrderID " + order_Id, true);

        } else if (OrderListState.equals("OrderListActivity")) {
            order_Id = String.valueOf(orderModel.getOrder_id());
            toolbarSection("#OrderID " + order_Id, true);
        }


        binding.viewpager1.setAdapter(new TabFragmentPagerAdapter(this.getSupportFragmentManager()));
        binding.tabs1.setupWithViewPager(binding.viewpager1);
        binding.tabs1.setTabGravity(TabLayout.GRAVITY_CENTER);


        Bundle bundle = new Bundle();
//From Scanner Activity

        bundle.putString("orderscanned", order_Id);
        bundle.putString("ID", Id);
        bundle.putString("ActivityState", scanState);

//From order Details Activity

        bundle.putParcelable("order", orderModel);
        bundle.putString("OrderLIST", OrderListState);

        Fragment fragment = null;


        fragment = new Items_Fragment();
        fragment.setArguments(bundle);
        fragment.setArguments(bundle);
        mFragmentArrayList.add(fragment);


        fragment = new LocationFragment();
        fragment.setArguments(bundle);
        fragment.setArguments(bundle);
        mFragmentArrayList.add(fragment);


        binding.viewpager1.setAdapter(new TabFragmentPagerAdapter(this.getSupportFragmentManager()));


    }


    public class TabFragmentPagerAdapter extends FragmentStatePagerAdapter {

        String[] title = new String[]{
                "ITEMS", "LOCATION"
        };


        TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {

            return position >= mFragmentArrayList.size() ? null : mFragmentArrayList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public int getCount() {
            return mFragmentArrayList.size();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            setResult(301);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void fetchNewOrderFromServer(int page) {
        String accessToken = BusinessDetailsGenerator.getInstance(this).getApi_token();
        String deviceId = BusinessDetailsGenerator.getInstance(this).getDeviceId();
        OrdersCloudManager orderListCloudManager = new OrdersCloudManager(this);
        orderListCloudManager.fetchNewOrders(accessToken, deviceId, "assigned", page, this);
    }

    @Override
    public void fetchNewOrder(AllOrderModel Assigned_orders) {
        orderData = Assigned_orders.getData();
        for (int i = 0; i < orderData.size(); i++) {
            oID = String.valueOf(orderData.get(i).getOrder_id());
            if (order_Id.equals(oID)) {
                Id = String.valueOf(orderData.get(i).getId()); //id is send as  a String it will be converted back in fragment
            }
        }
    }

    @Override
    public void fetchNewOrderListFailed(String errorMessage) {

    }

}