package com.e.sholinpaul.grandgroc.cloud.CloudCallBAck;

import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.model.Model.PlaceModel;
import com.e.sholinpaul.grandgroc.model.Model.ProductModel;

import java.util.List;

public interface CheckAssignedOrderListener extends BaseListener {

    void fetchCheckedAssignedOrderDetails( PlaceModel  order, List<ProductModel> order_details,OrderModel AssignedOrder);

    void fetchCheckAssignedOrderDetailsFailed(String errorMessage);

}
