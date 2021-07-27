package com.e.sholinpaul.grandgroc.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.e.sholinpaul.grandgroc.R;
import com.e.sholinpaul.grandgroc.databinding.FragmentDashboardBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {


    private FragmentDashboardBinding binding;
    ArrayList<Fragment> mFragmentArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();
        return root;
    }

    private void init() {
        setupViewPager();
        CustomTabView();
    }


    public void CustomTabView() {

        binding.tabs.getTabAt(0).setCustomView(R.layout.tab_neworder);
        TextView tView11 = (TextView) binding.tabs.getTabAt(0).getCustomView().findViewById(R.id.tv_tabName1);
        tView11.setText("NEW ORDERS");

        binding.tabs.getTabAt(1).setCustomView(R.layout.tab_currentorder);
        TextView tView1 = (TextView) binding.tabs.getTabAt(1).getCustomView().findViewById(R.id.tv_tabName2);
        tView1.setText("CURRENT ORDERS");

    }


    private void setupViewPager() {

        binding.viewpager.setAdapter(new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager()));
        binding.tabs.setupWithViewPager(binding.viewpager);
        binding.tabs.setTabGravity(TabLayout.GRAVITY_CENTER);


        Fragment fragment = null;

        fragment = new NewOrderFragment();
        mFragmentArrayList.add(fragment);


        fragment = new CurrentOrderFragment();
        mFragmentArrayList.add(fragment);


        binding.viewpager.setAdapter(new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager()));


    }

    public class TabFragmentPagerAdapter extends FragmentStatePagerAdapter {

        String[] title = new String[]{
                "CURRENT  ORDERS", "NEW  ORDERS"
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}