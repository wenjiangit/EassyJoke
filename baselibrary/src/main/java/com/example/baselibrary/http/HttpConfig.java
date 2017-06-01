package com.example.baselibrary.http;

/**
 * Created by douliu on 2017/5/24.
 */

public class HttpConfig {

    private int mConnectTimeOut;

    private int mReadTimeOut;

    private HttpExecutor mHttpExecutor;

    public HttpConfig() {
        mHttpExecutor = new OkHttpExecutor();
        mConnectTimeOut = 10 * 1000;
        mReadTimeOut = 10 * 1000;
    }

    public int getConnectTimeOut() {
        return mConnectTimeOut;
    }

    public HttpConfig setConnectTimeOut(int connectTimeOut) {
        mConnectTimeOut = connectTimeOut;
        return this;
    }

    public int getReadTimeOut() {
        return mReadTimeOut;
    }

    public HttpConfig setReadTimeOut(int readTimeOut) {
        mReadTimeOut = readTimeOut;
        return this;
    }

    public HttpExecutor getHttpExecutor() {
        return mHttpExecutor;
    }

    public HttpConfig setHttpExecutor(HttpExecutor httpExecutor) {
        mHttpExecutor = httpExecutor;
        return this;
    }
}
