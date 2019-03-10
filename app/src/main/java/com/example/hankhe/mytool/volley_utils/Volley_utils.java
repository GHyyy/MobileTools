package com.example.hankhe.mytool.volley_utils;

import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hankhe on 2016/11/8.
 */
public class Volley_utils {

    private String url;
    private TextView tv_show;
    private TextView tvResponse;

    public Volley_utils(String url, TextView tv_show,TextView tvResponse){
        this.url=url;
        this.tv_show=tv_show;
        this.tvResponse=tvResponse;
    }

    public String getUrl() {
        return url;
    }

    public TextView getTv_show() {
        return tv_show;
    }

    public TextView getTvResponse() {
        return tvResponse;
    }

    public void doPost(){

            StringRequest request2=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {

                    tv_show.setText(s);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    tv_show.setText(volleyError.toString());
                }
            });
//            {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put("pkg", "com.tct.diagnostics");
//                    map.put("apks", "[{\"pkg\": \"com.tct.timetool\",\"vname\": \"1.0.1.1212.999\",\"vcode\":1}]");
//                    map.put("sign", "#87&$#1@99");
//                    return map;
//                }
//            };
        request2.setTag("post");
        MyApplication.getHttpQueue().add(request2);

        }

    public void volleyGet(){
        StringRequest request1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {


                Log.i("aa", "请求成功" + s);

                tv_show.setText(s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.i("aa", "请求失败" + volleyError.toString());

                tv_show.setText(volleyError.toString());
            }
        });

        //设置取消http请求
        request1.setTag("volleyget");
        MyApplication.getHttpQueue().add(request1);
    }
}
