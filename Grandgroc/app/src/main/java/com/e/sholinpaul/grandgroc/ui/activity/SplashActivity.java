package com.e.sholinpaul.grandgroc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.e.sholinpaul.grandgroc.databinding.ActivitySplashBinding;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;
import com.e.sholinpaul.grandgroc.utils.MyShareprefUtility;

public class SplashActivity extends BaseActivity {
    ActivitySplashBinding binding;
    public MyShareprefUtility myShareprefUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        boolean isConnected = checkConnection(this);

        if (!isConnected) {
            showNoConnectionSnackBar("No internet Connection");
            return;
        }
        String text1 = "Discover the best and fresh grocery items\n" +
                "from Grandgroc grocery app and fast\n" +
                "delivery to your doorstep";
        binding.tvContent.setText(text1);

        binding.btnGotoLogin.setOnClickListener(view1 -> {
            if (doubleClickPrevent()) {

                if (savedInstanceState == null) {

                    myShareprefUtility = new MyShareprefUtility(this);
                    loginCheck();

                }
            }
        });

    }

    private void loginCheck() {
        boolean isLoggedIn;
        isLoggedIn = isLoggedIn();
        if (isLoggedIn) {
            goToDashboard();
        } else {
            goToLogin();
        }

    }

    private boolean isLoggedIn() {
        String Username = BusinessDetailsGenerator.getInstance(this).getName();
        return Username != null && !TextUtils.isEmpty(Username);
    }

    private void goToLogin() {
        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    private void goToDashboard() {
        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}