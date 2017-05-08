package com.example.baselibrary.utils;

import android.content.Context;

/**
 * Created by douliu on 2017/5/5.
 */

public class DensityUtil {

    public static int dp2px(Context context,int dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
