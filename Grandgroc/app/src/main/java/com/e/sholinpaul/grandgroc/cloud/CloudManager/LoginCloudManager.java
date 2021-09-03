package com.e.sholinpaul.grandgroc.cloud.CloudManager;

import android.content.Context;

import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.LoginListener;
import com.e.sholinpaul.grandgroc.cloud.SPManager.LoginSPManager;
import com.e.sholinpaul.grandgroc.model.Model.LoginModel;
import com.e.sholinpaul.grandgroc.model.Responses.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginCloudManager extends BaseCloudManger {
    public LoginCloudManager(Context mContext) {
        super(mContext);
    }

    public void fetchLogin(final LoginModel delivery_details, final LoginListener listener) {
        if (!checkConnection(listener)) {
            return;
        }
        Call<LoginResponse> call = api.signInWith(delivery_details);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                LoginResponse loginResponse = response.body();
                if (loginResponse != null) {
                    if (loginResponse.success == "true") {
                        LoginSPManager loginSPManager = new LoginSPManager(mContext);
                        loginSPManager.saveLoginDetailsToSP(loginResponse.delivery_details);
                        if (listener != null) {
                            listener.fetchLoginDetails(loginResponse.delivery_details, loginResponse.msg);
                        }
                    } else {
                        if (listener != null) {
                            listener.onCompleted();
                            listener.fetchLoginDetailsFailed(loginResponse.success, loginResponse.msg);
                        }
                    }
                    if (listener != null) {
                        listener.onCompleted();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                if (listener != null) {
                    listener.onCompleted();
                    listener.fetchLoginDetailsFailed("Failed", t.getMessage());
                }
            }
        });

//        if (listener != null) {
//            listener.onStarted();
//        }


    }


}
