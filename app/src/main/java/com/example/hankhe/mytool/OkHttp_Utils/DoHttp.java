package com.example.hankhe.mytool.OkHttp_Utils;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hankhe on 2017/2/28.
 */
public class DoHttp {
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private OkHttpClient mOkHttpclient;
    private String mTag = "Hank";
    private TextView tvRequest,tvResponse;
    private String json;

    public DoHttp(TextView tvRequest,TextView tvResponse){
        this.tvRequest = tvRequest;
        this.tvResponse = tvResponse;
        mOkHttpclient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();
    }

    /**
     * Get
     * @param mUrl
     * **/
    public String doGet(String mUrl){
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(mUrl).build();
        execute(request);
        return json;
    }

    /**
     * Post    提交String
     *@param mUrl
     *@param mBody
     * **/
    public void doPostString(String mUrl,String mBody){
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN,mBody);
        Request request = new Request.Builder()
                .url(mUrl)
                .post(requestBody)
                .build();
        execute(request);
        Log.v(mTag, "+++  doPost  +++");
    }

    /**
     * doPostKv 提交键值对  key - value
     * @param mUrl
     * **/
    public String doPostKv(String mUrl){
        FormBody.Builder builder = new FormBody.Builder();
            builder.add("key","value");
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(mUrl)
                .post(requestBody)
                .build();
        execute(request);
        return json;
    }

    /**
     * Call
     * **/
    private void execute(Request request){
        Call call = mOkHttpclient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String faild = e.toString();
                json = e.toString();
                Log.v(mTag,json);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                json = response.body().string();
                Log.v(mTag,json);
            }
        });
    }
}
