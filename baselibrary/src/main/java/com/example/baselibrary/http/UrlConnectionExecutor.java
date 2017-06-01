package com.example.baselibrary.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by douliu on 2017/5/24.
 */

public class UrlConnectionExecutor implements HttpExecutor{

    @Override
    public HttpURLConnection openConnection(URL url, Proxy proxy) throws Exception {
        if (proxy != null) {
            return (HttpURLConnection) url.openConnection(proxy);
        }
        return (HttpURLConnection) url.openConnection();
    }
}
