package com.example.hankhe.mytool.OkHttp_Utils;

import android.content.Context;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hankhe on 2017/2/24.
 */
public class RequestManager {
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/json; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final String TAG = RequestManager.class.getSimpleName();
    private static final String BASE_URL = "http://xxx.com/openapi";//请求接口根地址
    private static volatile RequestManager mInstance;//单利引用
    public static final int TYPE_GET = 0;
    public static final int TYPE_POST_JSON = 1;
    public static final int TYPE_POST_FORM = 2;
    private OkHttpClient mOkHttpClient;//okHttpClient 实例
    private Handler okHttpHandler;//全局处理子线程和M主线程通信

    public RequestManager(Context context){
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
        okHttpHandler = new Handler(context.getMainLooper());
    }

    /**
     * 获取单例引用 双重检查锁实现单例
     * @return
     * **/
    public static RequestManager getInstance(Context context){
        RequestManager inst = mInstance;
        if (inst == null) {
            synchronized (RequestManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new RequestManager(context.getApplicationContext());
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    /**
     * Http 同步请求
     * @param actionUrl
     * @param requestType
     * @param paramsMap
     * **/
    public void requestSyn(String actionUrl, int requestType, HashMap<String, String> paramsMap){
        switch (requestType){
            case TYPE_GET:
                requestGetBySyn(actionUrl,paramsMap);
                break;
            case TYPE_POST_JSON:

                break;
            case TYPE_POST_FORM:
                break;
        }
    }

    /**
     * Get 请求
     * @param actionUrl
     * @param paramsMap
     * **/
    private void requestGetBySyn(String actionUrl,HashMap<String,String> paramsMap){
        StringBuilder tempParams = new StringBuilder();
        int pos = 0;

            try {
                for (String key : paramsMap.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }

                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                    pos++;
                }

                String requestUrl = String.format("%s/%s?%s", BASE_URL, actionUrl, tempParams.toString());
                Request request = addHeaders().url(requestUrl).build();
                Call call = mOkHttpClient.newCall(request);
                Response response = call.execute();
                String result = response.body().toString();
                Log.v("response",result);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e(TAG,e.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }

    }

    /**
     * 添加请求头
     * **/
    private Request.Builder addHeaders(){
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("platform", "2")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "3.2.0");
        return builder;
    }

    /**
     * 接口：ReqCallBack
     * **/
    public interface ReqCallBack<T>{

        void onReqSuccess(T result);
        void onReqFailed(String errorMsg);
    }
    /**
     * 回调successCallBack
     * @param result
     * @param callBack
     * **/
    private <T> void successCallBack(final T result, final ReqCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null){
                    callBack.onReqSuccess(result);
                }
            }
        });
    }

    /**
     * 回调failedCallBack
     * @param errorMsg
     * @param callBack
     * **/
    private <T> void failedCallBack(final String errorMsg, final ReqCallBack<T> callBack){
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack == null){
                    callBack.onReqFailed(errorMsg);
                }
            }
        });
    }
}
