package com.e.sholinpaul.grandgroc.cloud.SPManager;

import android.content.Context;

import com.e.sholinpaul.grandgroc.model.Model.LoginModel;
import com.e.sholinpaul.grandgroc.utils.Constants;
import com.e.sholinpaul.grandgroc.utils.MyShareprefUtility;

public class LoginSPManager {

    public Context mContext;
    MyShareprefUtility mPref;

    public LoginSPManager(Context mContext) {
        this.mContext = mContext;
        this.mPref = new MyShareprefUtility(mContext);
    }


    public void saveLoginDetailsToSP(LoginModel data) {

        if (data != null) {
            mPref.setInt(Constants.SharedPrefKeys.ID, data.getId());
            mPref.setString(Constants.SharedPrefKeys.NAME, data.getName());
            mPref.setString(Constants.SharedPrefKeys.EMAIL, data.getEmail());
            mPref.setString(Constants.SharedPrefKeys.PASSWORD, data.getPassword());
            mPref.setString(Constants.SharedPrefKeys.PASSWORD_STRING, data.getPassword_string());
            mPref.setString(Constants.SharedPrefKeys.UPDATED_AT, data.getUpdated_at());
            mPref.setString(Constants.SharedPrefKeys.STATUS, data.getStatus());
            mPref.setString(Constants.SharedPrefKeys.PHONE, data.getPhone());
            mPref.setString(Constants.SharedPrefKeys.LOCATION, data.getLocation());
            mPref.setString(Constants.SharedPrefKeys.EXPIRY_TIME, data.getExpiry_time());
            mPref.setString(Constants.SharedPrefKeys.EMPLOYEE_CODE, data.getEmployee_code());
            mPref.setString(Constants.SharedPrefKeys.DEVICE_ID, data.getDevice_id());
            mPref.setString(Constants.SharedPrefKeys.CREATED_AT, data.getCreated_at());
            mPref.setString(Constants.SharedPrefKeys.API_TOKEN, data.getApi_token());
            mPref.setString(Constants.SharedPrefKeys.PROFILE_PIC, data.getProfile_pic());

        }

    }


}
