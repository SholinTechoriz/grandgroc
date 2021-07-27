package com.e.sholinpaul.grandgroc.utils;

import android.content.Context;

import com.e.sholinpaul.grandgroc.model.Model.LoginModel;

public class BusinessDetailsGenerator {

    private static BusinessDetailsGenerator instance;
    private static MyShareprefUtility myShareprefUtility;

    public static BusinessDetailsGenerator getInstance(Context ctx) {
        if (instance == null) {
            instance = new BusinessDetailsGenerator();
            myShareprefUtility = new MyShareprefUtility(ctx);
        }
        return instance;
    }


    public int getId() {
        return myShareprefUtility.getInt(Constants.SharedPrefKeys.ID);
    }

    public void setId(int id) {
        myShareprefUtility.setInt(Constants.SharedPrefKeys.ID, id);
    }

    public String getName() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.NAME);
    }

    public void setName(String name) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.NAME, name);
    }


    public String getApi_token() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.API_TOKEN);
    }

    public void setApi_token(String api_token) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.API_TOKEN, api_token);
    }

    public String getCreatedAt() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.CREATED_AT);
    }

    public void setCreatedAt(String created_at) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.CREATED_AT, created_at);
    }

    public String getDeviceId() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.DEVICE_ID);
    }

    public void setDeviceId(String deviceId) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.DEVICE_ID, deviceId);
    }

    public String getEmail() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.EMAIL);
    }

    public void setEmail(String email) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.EMAIL, email);
    }

    public String getEmployeeCode() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.EMPLOYEE_CODE);
    }

    public void setEmployeeCode(String employeeCode) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.EMPLOYEE_CODE, employeeCode);
    }

    public String getExpiryTime() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.EXPIRY_TIME);
    }

    public void setExpiryTime(String expiryTime) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.EXPIRY_TIME, expiryTime);
    }

    public String getLocation() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.LOCATION);
    }

    public void setLocation(String location) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.LOCATION, location);
    }

    public String getPassword() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.PASSWORD);
    }

    public void setPassword(String password) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.PASSWORD, password);
    }

    public String getPasswordString() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.PASSWORD_STRING);
    }

    public void setPasswordString(String passwordString) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.PASSWORD_STRING, passwordString);
    }

    public String getPhone() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.PHONE);
    }

    public void setPhone(String phone) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.PHONE, phone);
    }

    public String getProfilePic() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.PROFILE_PIC);
    }

    public void setProfilePic(String profilePic) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.PROFILE_PIC, profilePic);
    }

    public String getStatus() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.STATUS);
    }

    public void setStatus(String status) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.STATUS, status);
    }

    public String getUpdatedAt() {
        return myShareprefUtility.getString(Constants.SharedPrefKeys.UPDATED_AT);
    }

    public void setUpdatedAt(String updatedAt) {
        myShareprefUtility.setString(Constants.SharedPrefKeys.UPDATED_AT, updatedAt);
    }

    public LoginModel getLoginModel() {
        LoginModel model = new LoginModel();
        model.setId(getId());
        model.setName(getName());
        model.setApi_token(getApi_token());
        model.setCreated_at(getCreatedAt());
        model.setDevice_id(getDeviceId());
        model.setEmail(getEmail());
        model.setEmployee_code(getEmployeeCode());
        model.setExpiry_time(getExpiryTime());
        model.setLocation(getLocation());
        model.setPassword(getPassword());
        model.setPassword_string(getPasswordString());
        model.setPhone(getPhone());
        model.setProfile_pic(getProfilePic());
        model.setStatus(getStatus());
        model.setUpdated_at(getUpdatedAt());
        return model;
    }

}
