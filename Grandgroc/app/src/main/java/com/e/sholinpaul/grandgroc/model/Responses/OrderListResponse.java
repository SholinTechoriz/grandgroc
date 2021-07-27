package com.e.sholinpaul.grandgroc.model.Responses;

import com.e.sholinpaul.grandgroc.model.Model.AllOrderListModel;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.model.Model.PlaceModel;
import com.e.sholinpaul.grandgroc.model.Model.ProductModel;

import java.util.List;

public class OrderListResponse {
    public String success;
    public OrderModel AssignedOrder;
    public PlaceModel order;
    public List<ProductModel>order_details;
}
