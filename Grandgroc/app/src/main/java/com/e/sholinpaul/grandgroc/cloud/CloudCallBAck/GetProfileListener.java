package com.e.sholinpaul.grandgroc.cloud.CloudCallBAck;

import com.e.sholinpaul.grandgroc.model.Model.LoginModel;

public interface GetProfileListener extends BaseListener {

    void fetchProfileDetails(LoginModel delivaryboy);

    void fetchProfileDetailsFailed(String errorMessage);
}
