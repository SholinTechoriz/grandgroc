package com.e.sholinpaul.grandgroc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.e.sholinpaul.grandgroc.R;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.LoginListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.LoginCloudManager;
import com.e.sholinpaul.grandgroc.databinding.ActivityLoginBinding;
import com.e.sholinpaul.grandgroc.model.Model.LoginModel;

import java.util.Base64;

public class LoginActivity extends BaseActivity implements LoginListener {
    ActivityLoginBinding binding;
    String encodedString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();



    }

    private void init() {
        binding.edUsername.setText("adilmycareer@gmail.com");
        binding.edPassword.setText("123123");
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doubleClickPrevent()) {
                    hideKeyboard(v);
                    validateLogin();

                }
            }
        });

    }

    private void validateLogin() {
        String username = binding.edUsername.getText().toString();
        String password = binding.edPassword.getText().toString();
        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);


        if (username.isEmpty()) {
            binding.edUsername.setError("Enter username");
            binding.edUsername.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            binding.edPassword.setError("Enter password");
            binding.edPassword.requestFocus();
            return;
        }
        String conco = "o63s";
        String appPassword = conco.concat(password);
        Log.d("LOKKKK", "system is..." + appPassword);
        Base64.Encoder encoder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encoder = Base64.getEncoder();
            encodedString = encoder.encodeToString(appPassword.getBytes());
            Log.d("LOKKKK", "system is..." + encodedString);

        }


        LoginModel loginModel = new LoginModel();
        loginModel.setEmail(username);
        loginModel.setDevice_id(androidId);
        Log.d("LOKKKK", "system id is..." + androidId);
        loginModel.setPassword(encodedString);

        LoginCloudManager loginCloudManager = new LoginCloudManager(this);
        loginCloudManager.fetchLogin(loginModel, this);

        binding.btnSignIn.setText(R.string.logging);
        binding.btnSignIn.setAlpha(BUTTON_ALPHA_VALUE_ENABLE);

        for (int i = 0; i < binding.rlMainLogin.getChildCount(); i++) {
            View view = binding.rlMainLogin.getChildAt(i);
            enableDisableView(view, false);
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
    public void fetchLoginDetails(LoginModel delivery_details, String message) {

        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();

        Log.d("qwerty", " data iss  :" + delivery_details.getEmail());


    }

    @Override
    public void fetchLoginDetailsFailed(String errorMsg, String message) {
        showMessage("error");
        binding.btnSignIn.setText(R.string.login);
        binding.btnSignIn.setVisibility(View.VISIBLE);
        binding.btnSignIn.setAlpha(BUTTON_ALPHA_VALUE_ENABLE);
        for (int i = 0; i < binding.rlMainLogin.getChildCount(); i++) {
            View view = binding.rlMainLogin.getChildAt(i);
            enableDisableView(view, true);
        }
    }
}