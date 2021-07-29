package com.e.sholinpaul.grandgroc.model.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductModel  implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("order_id")
    private int order_id;
    @SerializedName("productvariant_id")
    private int productvariant_id;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("variant_type")
    private String variant_type;
    @SerializedName("variant_unit")
    private String variant_unit;
    @SerializedName("price")
    private String price;
    @SerializedName("amount")
    private String amount;
    @SerializedName("status")
    private String status;
    @SerializedName("status_on")
    private String status_on;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("deleted_at")
    private String deleted_at;
    @SerializedName("product_name")
    private String product_name;
    @SerializedName("product_image")
    private String product_image;

    public  ProductModel(){

    }

    protected ProductModel(Parcel in) {
        id = in.readInt();
        order_id = in.readInt();
        productvariant_id = in.readInt();
        quantity = in.readInt();
        variant_type = in.readString();
        variant_unit = in.readString();
        price = in.readString();
        amount = in.readString();
        status = in.readString();
        status_on = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        deleted_at = in.readString();
        product_name = in.readString();
        product_image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(order_id);
        dest.writeInt(productvariant_id);
        dest.writeInt(quantity);
        dest.writeString(variant_type);
        dest.writeString(variant_unit);
        dest.writeString(price);
        dest.writeString(amount);
        dest.writeString(status);
        dest.writeString(status_on);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(deleted_at);
        dest.writeString(product_name);
        dest.writeString(product_image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
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

    public int getProductvariant_id() {
        return productvariant_id;
    }

    public void setProductvariant_id(int productvariant_id) {
        this.productvariant_id = productvariant_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getVariant_type() {
        return variant_type;
    }

    public void setVariant_type(String variant_type) {
        this.variant_type = variant_type;
    }

    public String getVariant_unit() {
        return variant_unit;
    }

    public void setVariant_unit(String variant_unit) {
        this.variant_unit = variant_unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_on() {
        return status_on;
    }

    public void setStatus_on(String status_on) {
        this.status_on = status_on;
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

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

}
