package com.example.baselibrary.utils;

import android.content.Context;

/**
 * Created by douliu on 2017/5/5.
 */

public class Utils {

    private static Context sContext;

    private Utils() {
    }

    public static void init(Context context) {
        Utils.sContext = context.getApplicationContext();
    }

    public static Context getContext() {
        if (sContext == null) {
            throw new NullPointerException("you should init in you application first");
        }
        return sContext;
    }

}
