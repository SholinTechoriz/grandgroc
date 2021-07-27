package com.e.sholinpaul.grandgroc.model.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PlaceModel implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("date")
    private String date;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
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

    protected PlaceModel(Parcel in) {
        id = in.readInt();
        date = in.readString();
        name = in.readString();
        phone = in.readString();
        location = in.readString();
        pin = in.readString();
        town = in.readString();
        landmark = in.readString();
        state_name = in.readString();
        country_name = in.readString();
        charges = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(location);
        dest.writeString(pin);
        dest.writeString(town);
        dest.writeString(landmark);
        dest.writeString(state_name);
        dest.writeString(country_name);
        dest.writeString(charges);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlaceModel> CREATOR = new Creator<PlaceModel>() {
        @Override
        public PlaceModel createFromParcel(Parcel in) {
            return new PlaceModel(in);
        }

        @Override
        public PlaceModel[] newArray(int size) {
            return new PlaceModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
