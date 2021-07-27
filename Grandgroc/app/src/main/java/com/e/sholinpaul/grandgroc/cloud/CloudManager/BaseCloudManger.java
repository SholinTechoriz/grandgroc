package com.e.sholinpaul.grandgroc.cloud.CloudManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.e.sholinpaul.grandgroc.RetrofitClient;
import com.e.sholinpaul.grandgroc.cloud.API_Grand_Groc;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.BaseListener;

public class BaseCloudManger {

    public Context mContext;
    public API_Grand_Groc api = RetrofitClient.getInstance().getLoginClient();


    public BaseCloudManger(Context mContext) {
        this.mContext = mContext;
    }


    public boolean checkConnection(BaseListener baseListener) {

        final ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            } else {
                if (baseListener != null) {
                    baseListener.onConnectionFailure(101);
                }
            }
        }
        return false;
    }
}
