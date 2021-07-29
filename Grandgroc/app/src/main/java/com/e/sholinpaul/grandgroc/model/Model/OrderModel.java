package com.e.sholinpaul.grandgroc.model.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OrderModel implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("order_id")
    private int order_id;
    @SerializedName("delivaryboy_id")
    private int delivaryboy_id;
    @SerializedName("store_id")
    private String store_id;
    @SerializedName("product_Ids")
    private String product_Ids;
    @SerializedName("type")
    private String type;
    @SerializedName("status")
    private String status;
    @SerializedName("assigned_On")
    private String assigned_On;
    @SerializedName("collected_on")
    private String collected_on;
    @SerializedName("delivered_on")
    private String delivered_on;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("date")
    private String date;
    @SerializedName("name")
    private String name;
    @SerializedName("delivarytype")
    private String delivarytype;
    @SerializedName("location")
    private String location;
    @SerializedName("pin")
    private String pin;
    @SerializedName("town")
    private String town;
    @SerializedName("landmark")
    private String landmark;
    @SerializedName("state_name")
    private String state_name;
    @SerializedName("country_name")
    private String country_name;
    @SerializedName("charges")
    private String charges;
    @SerializedName("deleted_at")
    private String deleted_at;

    @SerializedName("Order_id")
    private int Order_id;
    @SerializedName("device_id")
    private String device_id;
    @SerializedName("api_token")
    private String api_token;

    @SerializedName("assignordersID")
    private int assignordersID;

    public  OrderModel(){

    }


    protected OrderModel(Parcel in) {
        id = in.readInt();
        order_id = in.readInt();
        delivaryboy_id = in.readInt();
        store_id = in.readString();
        product_Ids = in.readString();
        type = in.readString();
        status = in.readString();
        assigned_On = in.readString();
        collected_on = in.readString();
        delivered_on = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        date = in.readString();
        name = in.readString();
        delivarytype = in.readString();
        location = in.readString();
        pin = in.readString();
        town = in.readString();
        landmark = in.readString();
        state_name = in.readString();
        country_name = in.readString();
        charges = in.readString();
        deleted_at = in.readString();
        Order_id = in.readInt();
        device_id = in.readString();
        api_token = in.readString();
        assignordersID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(order_id);
        dest.writeInt(delivaryboy_id);
        dest.writeString(store_id);
        dest.writeString(product_Ids);
        dest.writeString(type);
        dest.writeString(status);
        dest.writeString(assigned_On);
        dest.writeString(collected_on);
        dest.writeString(delivered_on);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(date);
        dest.writeString(name);
        dest.writeString(delivarytype);
        dest.writeString(location);
        dest.writeString(pin);
        dest.writeString(town);
        dest.writeString(landmark);
        dest.writeString(state_name);
        dest.writeString(country_name);
        dest.writeString(charges);
        dest.writeString(deleted_at);
        dest.writeInt(Order_id);
        dest.writeString(device_id);
        dest.writeString(api_token);
        dest.writeInt(assignordersID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderModel> CREATOR = new Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel in) {
            return new OrderModel(in);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getDelivaryboy_id() {
        return delivaryboy_id;
    }

    public void setDelivaryboy_id(int delivaryboy_id) {
        this.delivaryboy_id = delivaryboy_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getProduct_Ids() {
        return product_Ids;
    }

    public void setProduct_Ids(String product_Ids) {
        this.product_Ids = product_Ids;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssigned_On() {
        return assigned_On;
    }

    public void setAssigned_On(String assigned_On) {
        this.assigned_On = assigned_On;
    }

    public String getCollected_on() {
        return collected_on;
    }

    public void setCollected_on(String collected_on) {
        this.collected_on = collected_on;
    }

    public String getDelivered_on() {
        return delivered_on;
    }

    public void setDelivered_on(String delivered_on) {
        this.delivered_on = delivered_on;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDelivarytype() {
        return delivarytype;
    }

    public void setDelivarytype(String delivarytype) {
        this.delivarytype = delivarytype;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }


    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }


    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public int getAssignordersID() {
        return assignordersID;
    }

    public void setAssignordersID(int assignordersID) {
        this.assignordersID = assignordersID;
    }
}
