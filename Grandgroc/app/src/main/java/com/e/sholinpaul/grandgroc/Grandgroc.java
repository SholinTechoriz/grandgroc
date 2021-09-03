package com.e.sholinpaul.grandgroc;

import android.app.Application;
import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Grandgroc extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "grandgroc");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

}
