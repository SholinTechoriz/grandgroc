package com.e.sholinpaul.grandgroc.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class CalanderUtilis {

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        return DateFormat.format("dd/ MM/ yyyy", cal).toString();
    }

    public static long daysExp(long thatDay) {

        Calendar today = Calendar.getInstance();
        long toDay = today.getTimeInMillis();

        return ((int) ((toDay / (24 * 60 * 60 * 1000)) - (int) (thatDay / (24 * 60 * 60 * 1000))));
    }


    public static String getSchedulerDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        return DateFormat.format("dd/MM/yyyy", cal).toString();
    }
    public static String getSchedulerTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        return DateFormat.format(" hh:mm a", cal).toString();
    }

    public static String getSchedulerDateAndTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        return DateFormat.format("dd/ MM/ yyyy hh:mm a", cal).toString();
    }

    public static String setBusinessRegExpDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        return DateFormat.format("dd/MM/yyyy hh:mm:ss a", cal).toString();

    }

}


