package com.example.wenjian.eassyjoke.test;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理
 * Created by douliu on 2017/5/27.
 */

public class MyInvokeHandler implements InvocationHandler{

    private ISubject mSubject;

    public MyInvokeHandler(ISubject subject) {
        mSubject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().equals("apply")) {
            Log.d("TAG","调用apply之前做些准备工作...");
            Object invoke = method.invoke(mSubject, args);
            Log.d("TAG","调用apply之后做些收尾工作...");
            return invoke;
        }

        return null;
    }
}
