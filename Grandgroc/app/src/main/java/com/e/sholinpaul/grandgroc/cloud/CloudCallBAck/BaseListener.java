package com.e.sholinpaul.grandgroc.cloud.CloudCallBAck;

public interface BaseListener {

    void onStarted();

    void onCompleted();

    void onConnectionFailure(int errorCode);

}
