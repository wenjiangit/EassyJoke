package com.example.baselibrary.http;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by douliu on 2017/5/24.
 */

public class HttpRequestTask {

    private HttpExecutor mHttpExecutor;

    private void request(Request request) {
        Request.Method method = request.getMethod();
        String urlStr = request.getUrl();
        Proxy proxy = request.getProxy();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = mHttpExecutor.openConnection(url, proxy);
            if (connection instanceof HttpsURLConnection) {
                // TODO: 2017/5/24  添加Https相关配置
            }
            connection.setRequestMethod(method.name());
            connection.setConnectTimeout(request.getConnectTimeout());
            connection.setReadTimeout(request.getReadTimeout());
            connection.setDoInput(true);
            setHeaders(connection, request.getHeaders());

            connection.connect();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaders(HttpURLConnection connection, Map<String, String> headers) {
        if (headers == null || headers.size() == 0) {
            return;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            connection.setRequestProperty(key, value);
        }
    }
}
