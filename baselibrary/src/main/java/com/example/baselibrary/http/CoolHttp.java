package com.example.baselibrary.http;

import android.content.Context;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by douliu on 2017/5/24.
 */

public class CoolHttp {


    private static HttpConfig sHttpConfig;

    private static Context sContext;

    public static void intialize(Context context) {
        sContext = context;
    }



}
