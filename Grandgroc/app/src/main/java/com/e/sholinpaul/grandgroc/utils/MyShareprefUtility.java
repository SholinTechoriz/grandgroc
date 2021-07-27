package com.e.sholinpaul.grandgroc.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MyShareprefUtility {

    private SharedPreferences mSharedPreferences;

    public MyShareprefUtility(Context mContext) {
        mSharedPreferences = mContext.getSharedPreferences("MyLoyalitySharedPref", 0);
    }


    public void setString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, -1);
    }


    public void clearString() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, -1);
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }
}
