package com.e.sholinpaul.grandgroc.cloud.CloudManager;

import android.content.Context;

import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.CheckAssignedOrderListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.GetOrderTypeListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.NewOrderListListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.OrderDetailsListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.PostStatusChangeListener;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.model.Responses.CheckAssignedOrderResponse;
import com.e.sholinpaul.grandgroc.model.Responses.GetTypeListResponse;
import com.e.sholinpaul.grandgroc.model.Responses.NewOrderResponse;
import com.e.sholinpaul.grandgroc.model.Responses.OrderListResponse;
import com.e.sholinpaul.grandgroc.model.Responses.PostChangeStatusResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersCloudManager extends BaseCloudManger {
    public OrdersCloudManager(Context mContext) {
        super(mContext);
    }

    public void fetchNewOrders(String api_token, String device_id, String filter_Status, int page, final NewOrderListListener listener) {
        if (!checkConnection(listener)) {
            return;
        }
        Call<NewOrderResponse> call = api.newOrderList(api_token, device_id, filter_Status, page);
        call.enqueue(new Callback<NewOrderResponse>() {
            @Override
            public void onResponse(Call<NewOrderResponse> call, Response<NewOrderResponse> response) {

                NewOrderResponse newOrderResponse = response.body();
                if (newOrderResponse != null) {
                    if (newOrderResponse.success == "true") {
                        if (listener != null) {
                            listener.fetchNewOrder(newOrderResponse.Assigned_orders);
                        }
                    } else {
                        if (listener != null) {
                            listener.onCompleted();
                            listener.fetchNewOrderListFailed(newOrderResponse.success);
                        }
                    }
                    if (listener != null) {
                        listener.onCompleted();
                    }
                }
            }

            @Override
            public void onFailure(Call<NewOrderResponse> call, Throwable t) {

                if (listener != null) {
                    listener.onCompleted();
                    listener.fetchNewOrderListFailed("Failed");
                }
            }
        });

//        if (listener != null) {
//            listener.onStarted();
//        }
    }


    public void fetchAllOrdersList(String device_id, String api_token, int id, int Order_id, final OrderDetailsListener listener) {
        if (!checkConnection(listener)) {
            return;
        }
        Call<OrderListResponse> call = api.AllOrderList(device_id, api_token, id, Order_id);
        call.enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {

                OrderListResponse orderListResponse = response.body();
                if (orderListResponse != null) {
                    if (orderListResponse.success == "true") {
                        if (listener != null) {
                            listener.fetchOrderDetails(orderListResponse.AssignedOrder, orderListResponse.order, orderListResponse.order_details);
                        }
                    } else {
                        if (listener != null) {
                            listener.onCompleted();
                            listener.fetchOrderDetailsFailed(orderListResponse.success);
                        }
                    }
                    if (listener != null) {
                        listener.onCompleted();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {

                if (listener != null) {
                    listener.onCompleted();
                    listener.fetchOrderDetailsFailed("Failed");
                }
            }
        });

//        if (listener != null) {
//            listener.onStarted();
//        }
    }


    public void fetchOrderTypes(String device_id, String api_token, final GetOrderTypeListener listener) {
        if (!checkConnection(listener)) {
            return;
        }
        Call<GetTypeListResponse> call = api.AllOrderTypes(device_id, api_token);
        call.enqueue(new Callback<GetTypeListResponse>() {
            @Override
            public void onResponse(Call<GetTypeListResponse> call, Response<GetTypeListResponse> response) {

                GetTypeListResponse getTypeListResponse = response.body();
                if (getTypeListResponse != null) {
                    if (getTypeListResponse.success == "true") {
                        if (listener != null) {
                            listener.fetchOrderTypes(getTypeListResponse.success, getTypeListResponse.deliverytypes);
                        }
                    } else {
                        if (listener != null) {
                            listener.onCompleted();
                            listener.fetchOrderTypesFailed(getTypeListResponse.success);
                        }
                    }
                    if (listener != null) {
                        listener.onCompleted();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetTypeListResponse> call, Throwable t) {

                if (listener != null) {
                    listener.onCompleted();
                    listener.fetchOrderTypesFailed("Failed");
                }
            }
        });

//        if (listener != null) {
//            listener.onStarted();
//        }
    }


    public void PostStatusByScanner(final OrderModel data, final PostStatusChangeListener listener) {
        if (!checkConnection(listener)) {
            return;
        }
        Call<PostChangeStatusResponse> call = api.PostChangeStatus(data);
        call.enqueue(new Callback<PostChangeStatusResponse>() {
            @Override
            public void onResponse(Call<PostChangeStatusResponse> call, Response<PostChangeStatusResponse> response) {
                PostChangeStatusResponse postChangeStatusResponse = response.body();
                if (postChangeStatusResponse != null) {
                    if (postChangeStatusResponse.success == "true") {
                        if (listener != null) {
                            listener.postChangeStatus(data, postChangeStatusResponse.Message);
                        }
                    } else {
                        if (listener != null) {
                            listener.onCompleted();
                            listener.postChangeStatusFailed(response.message());
                        }
                    }
                    if (listener != null) {
                        listener.onCompleted();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostChangeStatusResponse> call, Throwable t) {
                if (listener != null) {
                    listener.onCompleted();
                    listener.postChangeStatusFailed("failed");
                }
            }
        });

    }

    public void PostChangeStatusBy(final OrderModel data, final PostStatusChangeListener listener) {
        if (!checkConnection(listener)) {
            return;
        }
        Call<PostChangeStatusResponse> call = api.PostChangeStatusBy(data);
        call.enqueue(new Callback<PostChangeStatusResponse>() {
            @Override
            public void onResponse(Call<PostChangeStatusResponse> call, Response<PostChangeStatusResponse> response) {
                PostChangeStatusResponse postChangeStatusResponse = response.body();
                if (postChangeStatusResponse != null) {
                    if (postChangeStatusResponse.success == "true") {
                        if (listener != null) {
                            listener.postChangeStatus(data, postChangeStatusResponse.Message);
                        }
                    } else {
                        if (listener != null) {
                            listener.onCompleted();
                            listener.postChangeStatusFailed(response.message());
                        }
                    }
                    if (listener != null) {
                        listener.onCompleted();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostChangeStatusResponse> call, Throwable t) {
                if (listener != null) {
                    listener.onCompleted();
                    listener.postChangeStatusFailed("failed");
                }
            }
        });

    }


    public void CheckAssignedOrder(String device_id, String api_token, int Order_id, final CheckAssignedOrderListener listener) {
        if (!checkConnection(listener)) {
            return;
        }
        Call<CheckAssignedOrderResponse> call = api.CheckAssignedOrder(device_id, api_token, Order_id);
        call.enqueue(new Callback<CheckAssignedOrderResponse>() {
            @Override
            public void onResponse(Call<CheckAssignedOrderResponse> call, Response<CheckAssignedOrderResponse> response) {

                CheckAssignedOrderResponse checkAssignedOrderResponse = response.body();
                if (checkAssignedOrderResponse != null) {
                    if (checkAssignedOrderResponse.success == "true") {
                        if (listener != null) {
                            listener.fetchCheckedAssignedOrderDetails(checkAssignedOrderResponse.order, checkAssignedOrderResponse.order_details);
                        }
                    } else {
                        if (listener != null) {
                            listener.onCompleted();
                            listener.fetchCheckAssignedOrderDetailsFailed(checkAssignedOrderResponse.success);
                        }
                    }
                    if (listener != null) {
                        listener.onCompleted();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckAssignedOrderResponse> call, Throwable t) {

                if (listener != null) {
                    listener.onCompleted();
                    listener.fetchCheckAssignedOrderDetailsFailed("Failed");
                }
            }
        });

//        if (listener != null) {
//            listener.onStarted();
//        }
    }


}
