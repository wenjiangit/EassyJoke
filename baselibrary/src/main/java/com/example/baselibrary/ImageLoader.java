package com.example.baselibrary;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by douliu on 2017/5/12.
 */

public class ImageLoader {

    public static void display(Context context, ImageView imageView, String url) {

        Glide.with(context).load(url).into(imageView);

    }
}
