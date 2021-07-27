package com.e.sholinpaul.grandgroc.model.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllOrderListModel implements Parcelable {

    @SerializedName("AssignedOrder")
    private OrderModel AssignedOrder;

    @SerializedName("order")
    private PlaceModel order;

    @SerializedName("order_details")
    private ArrayList<ProductModel> order_details = new ArrayList<ProductModel>();

    protected AllOrderListModel(Parcel in) {
        AssignedOrder = in.readParcelable(OrderModel.class.getClassLoader());
        order = in.readParcelable(OrderModel.class.getClassLoader());
        order_details = in.createTypedArrayList(ProductModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(AssignedOrder, flags);
        dest.writeParcelable(order, flags);
        dest.writeTypedList(order_details);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AllOrderListModel> CREATOR = new Creator<AllOrderListModel>() {
        @Override
        public AllOrderListModel createFromParcel(Parcel in) {
            return new AllOrderListModel(in);
        }

        @Override
        public AllOrderListModel[] newArray(int size) {
            return new AllOrderListModel[size];
        }
    };

    public OrderModel getAssignedOrder() {
        return AssignedOrder;
    }

    public void setAssignedOrder(OrderModel assignedOrder) {
        AssignedOrder = assignedOrder;
    }

    public PlaceModel getOrder() {
        return order;
    }

    public void setOrder(PlaceModel order) {
        this.order = order;
    }

    public ArrayList<ProductModel> getOrder_details() {
        return order_details;
    }

    public void setOrder_details(ArrayList<ProductModel> order_details) {
        this.order_details = order_details;
    }
}
