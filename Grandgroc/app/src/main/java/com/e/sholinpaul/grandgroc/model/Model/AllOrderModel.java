package com.e.sholinpaul.grandgroc.model.Model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
public class AllOrderModel implements Parcelable {
    @SerializedName("current_page")
    private int current_page;
    @SerializedName("data")
    private ArrayList<OrderModel> data = new ArrayList<OrderModel>();
    @SerializedName("first_page_url")
    private String first_page_url;
    @SerializedName("from")
    private int from;
    @SerializedName("last_page")
    private int last_page;
    @SerializedName("last_page_url")
    private String last_page_url;
    @SerializedName("next_page_url")
    private String next_page_url;
    @SerializedName("path")
    private String path;
    @SerializedName("per_page")
    private int per_page;
    @SerializedName("prev_page_url")
    private String prev_page_url;
    @SerializedName("to")
    private int to;
    @SerializedName("total")
    private int total;

    protected AllOrderModel(Parcel in) {
        current_page = in.readInt();
        first_page_url = in.readString();
        from = in.readInt();
        last_page = in.readInt();
        last_page_url = in.readString();
        next_page_url = in.readString();
        path = in.readString();
        per_page = in.readInt();
        prev_page_url = in.readString();
        to = in.readInt();
        total = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(current_page);
        dest.writeString(first_page_url);
        dest.writeInt(from);
        dest.writeInt(last_page);
        dest.writeString(last_page_url);
        dest.writeString(next_page_url);
        dest.writeString(path);
        dest.writeInt(per_page);
        dest.writeString(prev_page_url);
        dest.writeInt(to);
        dest.writeInt(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AllOrderModel> CREATOR = new Creator<AllOrderModel>() {
        @Override
        public AllOrderModel createFromParcel(Parcel in) {
            return new AllOrderModel(in);
        }

        @Override
        public AllOrderModel[] newArray(int size) {
            return new AllOrderModel[size];
        }
    };

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public ArrayList<OrderModel> getData() {
        return data;
    }

    public void setData(ArrayList<OrderModel> data) {
        this.data = data;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
