package com.tyas.test.assignyas.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

/**
 * Created by tyas on 7/21/16.
 */
public class ScreenUtil {

    private DisplayMetrics displayMetrics;

    public ScreenUtil(Context context) {
        displayMetrics = new DisplayMetrics();
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    public int getCurrentWidth() {
        if (displayMetrics != null) {
            return displayMetrics.widthPixels;
        } else {
            throw new RuntimeException("DisplayMetric not declared");
        }
    }

    public int getCurrentHeight() {
        if (displayMetrics != null) {
            return displayMetrics.heightPixels;
        } else {
            throw new RuntimeException("DisplayMetric not declared");
        }
    }
}
