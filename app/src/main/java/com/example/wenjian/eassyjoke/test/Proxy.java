package com.example.wenjian.eassyjoke.test;

import java.lang.reflect.InvocationHandler;

/**
 * Created by douliu on 2017/5/27.
 */

public class Proxy implements ISubject{

    private ISubject mSubject;

    public Proxy(ISubject subject) {
        mSubject = subject;
    }

    @Override
    public void apply() {
        mSubject.apply();
    }



}
