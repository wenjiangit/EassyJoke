package com.example.baselibrary.fixbug;

import android.content.Context;

/**
 * Created by wenjian on 2017/5/1.
 */

public class DexFixManager {

    private Context mContext;

    public DexFixManager(Context context) {
        mContext = context;
    }

    public void fixDex(String fixDexPath) {

        ClassLoader applicationClassLoader = mContext.getClassLoader();


    }
}
