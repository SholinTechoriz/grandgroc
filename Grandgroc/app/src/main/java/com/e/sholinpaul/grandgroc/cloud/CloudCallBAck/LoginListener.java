package com.e.sholinpaul.grandgroc.cloud.CloudCallBAck;

import com.e.sholinpaul.grandgroc.model.Model.LoginModel;

public interface LoginListener extends BaseListener {

    void fetchLoginDetails(LoginModel delivery_details, String message);

    void fetchLoginDetailsFailed(String errorMsg, String message);
}
