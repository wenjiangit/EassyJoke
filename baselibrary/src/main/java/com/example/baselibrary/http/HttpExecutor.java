package com.example.baselibrary.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by douliu on 2017/5/24.
 */

public interface HttpExecutor {

    HttpURLConnection openConnection(URL url, Proxy proxy) throws Exception;

}
