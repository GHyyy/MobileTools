package com.example.hankhe.mytool.volley_utils;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by hankhe on 2016/11/1.
 */
public class MyApplication  extends Application{
    public static RequestQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();

        queue= Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getHttpQueue(){
        return queue;
    }
}
