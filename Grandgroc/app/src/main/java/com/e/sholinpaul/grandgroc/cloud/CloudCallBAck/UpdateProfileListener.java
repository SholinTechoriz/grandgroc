package com.e.sholinpaul.grandgroc.cloud.CloudCallBAck;

import com.e.sholinpaul.grandgroc.model.Model.LoginModel;

public interface UpdateProfileListener extends BaseListener {

    void updateProfileDetails(LoginModel delivaryboy, String message);

    void updateProfileDetailsFailed(String errorMsg, String message);
}
