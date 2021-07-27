package com.e.sholinpaul.grandgroc.cloud.CloudCallBAck;

import com.e.sholinpaul.grandgroc.model.Model.AllOrderListModel;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.model.Model.PlaceModel;
import com.e.sholinpaul.grandgroc.model.Model.ProductModel;

import java.util.List;

public interface OrderDetailsListener extends BaseListener {

    void fetchOrderDetails(OrderModel AssignedOrder, PlaceModel  order, List<ProductModel> order_details);

    void fetchOrderDetailsFailed(String errorMessage);

}
