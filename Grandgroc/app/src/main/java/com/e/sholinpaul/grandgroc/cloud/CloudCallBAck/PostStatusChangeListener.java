package com.e.sholinpaul.grandgroc.cloud.CloudCallBAck;

import com.e.sholinpaul.grandgroc.model.Model.OrderModel;

public interface PostStatusChangeListener extends  BaseListener {

    void postChangeStatus(OrderModel data, String Message);

    void postChangeStatusFailed(String errorMsg);
}
