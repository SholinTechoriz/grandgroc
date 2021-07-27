package com.e.sholinpaul.grandgroc.model.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LoginModel implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("location")
    private String location;
    @SerializedName("employee_code")
    private String employee_code;
    @SerializedName("status")
    private String status;
    @SerializedName("phone")
    private String phone;
    @SerializedName("profile_pic")
    private String profile_pic;
    @SerializedName("password_string")
    private String password_string;
    @SerializedName("creator")
    private int creator;
    @SerializedName("vendor_type")
    private String vendor_type;
    @SerializedName("store_id")
    private int store_id;
    @SerializedName("expiry_time")
    private String expiry_time;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("email")
    private String email;
    @SerializedName("api_token")
    private String api_token;
    @SerializedName("access_token")
    private String access_token;
    @SerializedName("password")
    private String password;
    @SerializedName("device_id")
    private String device_id;

    @SerializedName("current_password")
    private String current_password;
    @SerializedName("new_password")
    private String new_password;
    @SerializedName("new_confirm_password")
    private String new_confirm_password;
    @SerializedName("filter_Status")
    private String filter_Status;


    public LoginModel() {

    }

    protected LoginModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        location = in.readString();
        employee_code = in.readString();
        status = in.readString();
        phone = in.readString();
        profile_pic = in.readString();
        password_string = in.readString();
        creator = in.readInt();
        vendor_type = in.readString();
        store_id = in.readInt();
        expiry_time = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        email = in.readString();
        api_token = in.readString();
        access_token = in.readString();
        password = in.readString();
        device_id = in.readString();
        current_password = in.readString();
        new_password = in.readString();
        new_confirm_password = in.readString();
        filter_Status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(employee_code);
        dest.writeString(status);
        dest.writeString(phone);
        dest.writeString(profile_pic);
        dest.writeString(password_string);
        dest.writeInt(creator);
        dest.writeString(vendor_type);
        dest.writeInt(store_id);
        dest.writeString(expiry_time);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(email);
        dest.writeString(api_token);
        dest.writeString(access_token);
        dest.writeString(password);
        dest.writeString(device_id);
        dest.writeString(current_password);
        dest.writeString(new_password);
        dest.writeString(new_confirm_password);
        dest.writeString(filter_Status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getPassword_string() {
        return password_string;
    }

    public void setPassword_string(String password_string) {
        this.password_string = password_string;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public String getVendor_type() {
        return vendor_type;
    }

    public void setVendor_type(String vendor_type) {
        this.vendor_type = vendor_type;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getExpiry_time() {
        return expiry_time;
    }

    public void setExpiry_time(String expiry_time) {
        this.expiry_time = expiry_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getCurrent_password() {
        return current_password;
    }

    public void setCurrent_password(String current_password) {
        this.current_password = current_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getNew_confirm_password() {
        return new_confirm_password;
    }

    public void setNew_confirm_password(String new_confirm_password) {
        this.new_confirm_password = new_confirm_password;
    }

    public String getFilter_Status() {
        return filter_Status;
    }

    public void setFilter_Status(String filter_Status) {
        this.filter_Status = filter_Status;
    }
}
