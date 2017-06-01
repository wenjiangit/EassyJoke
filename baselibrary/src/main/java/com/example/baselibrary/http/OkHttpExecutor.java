package com.example.baselibrary.http;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by douliu on 2017/5/24.
 */

public class OkHttpExecutor implements HttpExecutor {
    @Override
    public HttpURLConnection openConnection(URL url, Proxy proxy) {
        return null;
    }
}
