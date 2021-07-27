package com.e.sholinpaul.grandgroc.cloud.CloudCallBAck;

import com.e.sholinpaul.grandgroc.model.Model.OrderModel;

import java.util.List;

public interface GetOrderTypeListener extends BaseListener {

    void fetchOrderTypes(String message, List<OrderModel> deliverytypes);

    void fetchOrderTypesFailed(String errorMessage);
}
