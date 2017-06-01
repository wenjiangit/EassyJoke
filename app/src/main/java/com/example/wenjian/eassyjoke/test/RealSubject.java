package com.example.wenjian.eassyjoke.test;

import android.util.Log;

/**
 * Created by douliu on 2017/5/27.
 */

public class RealSubject implements ISubject {
    @Override
    public void apply() {
        Log.d("TAG","apply 被调用了");
    }
}
