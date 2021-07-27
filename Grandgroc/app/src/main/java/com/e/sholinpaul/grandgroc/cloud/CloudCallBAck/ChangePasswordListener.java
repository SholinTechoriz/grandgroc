package com.e.sholinpaul.grandgroc.cloud.CloudCallBAck;

import com.e.sholinpaul.grandgroc.model.Model.LoginModel;

public interface ChangePasswordListener extends BaseListener {

    void ChangePassword(LoginModel data, String message);

    void ChangePasswordFailed(String errorMsg, String errorMessage);
}
