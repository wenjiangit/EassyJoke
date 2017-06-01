package com.example.baselibrary.http;

import java.net.Proxy;
import java.util.List;
import java.util.Map;

/**
 * Created by douliu on 2017/5/24.
 */

public class Request {

    private String mUrl;

    private Map<String,String> mHeaders;

    private Map<String, String> mParams;

    private Method mMethod;

    private Proxy mProxy;

    private int mConnectTimeout;

    private int mReadTimeout;

    public Request(String url, Method method) {
        mUrl = url;
        mMethod = method;
    }

    public Request(String url) {
        this(url, Method.GET);
    }

    public Proxy getProxy() {
        return mProxy;
    }

    public void setProxy(Proxy proxy) {
        mProxy = proxy;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public void setParams(Map<String, String> params) {
        mParams = params;
    }

    public int getConnectTimeout() {
        return mConnectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        mConnectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return mReadTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        mReadTimeout = readTimeout;
    }

    enum Method {
        GET,POST,PUT,DELETE
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Method getMethod() {
        return mMethod;
    }

    public void setMethod(Method method) {
        mMethod = method;
    }
}
