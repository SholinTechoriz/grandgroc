package com.e.sholinpaul.grandgroc.cloud.CloudCallBAck;

import com.e.sholinpaul.grandgroc.model.Model.AllOrderModel;

public interface NewOrderListListener extends BaseListener {
    void fetchNewOrder(AllOrderModel Assigned_orders);

    void fetchNewOrderListFailed(String errorMessage);
}
