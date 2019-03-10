package com.example.hankhe.mytool.Http_Native;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by hankhe on 2017/1/6.
 */
public class AsyTaskHttp {
    private String responseStr;
    private String mUrl;


    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }

    public String getResponseStr() {

        return responseStr;
    }



    /**http get请求 **/
    public void httpGet(String url,TextView responseStr){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL httpUrl=new URL(params[0]);
                    URLConnection connection=httpUrl.openConnection();
                    connection.setReadTimeout(5000);
                    InputStream in=connection.getInputStream();
                    InputStreamReader reader=new InputStreamReader(in,"utf-8");
                    BufferedReader bs=new BufferedReader(reader);
                    String str;
                    StringBuilder builder =new StringBuilder();
                    while((str=bs.readLine())!=null){
                        builder.append(str);
                        System.out.println("reader:" + str);
                        Log.e("Hank", str);
                    }

                    bs.close();
                    reader.close();
                    in.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }.execute(url);
    }

    /**http post请求**/
    private void httpPost(){
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    URL postUrl=new URL(params[0]);
                    HttpURLConnection con= (HttpURLConnection) postUrl.openConnection();
                    con.setRequestMethod("POST");
                    con.setReadTimeout(5000);
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    OutputStream outputStream=con.getOutputStream();
                    OutputStreamWriter writer=new OutputStreamWriter(outputStream,"utf-8");
                    BufferedWriter bs=new BufferedWriter(writer);
                    bs.write("keyfrom=jktestss&key=349253901&type=data&doctype=json&version=1.1&q=good");
                    bs.flush();

                    // 读取数据
                    InputStream in=con.getInputStream();
                    InputStreamReader reader=new InputStreamReader(in,"utf-8");
                    BufferedReader bb=new BufferedReader(reader);
                    String st;
                    StringBuilder builder=new StringBuilder();
                    while((st=bb.readLine())!=null){
                        builder.append(st);
                    }
                    // JSON
                    JSONObject object=new JSONObject(builder.toString());
                    System.out.println("translation" + object.get("translation"));
                    JSONArray array_1=object.getJSONArray("web");
                    for(int i=0;i<array_1.length();i++){
                        JSONObject bas=array_1.getJSONObject(i);
                        System.out.println("++++++++++++++++++++++++++++++++++");
                        System.out.println("value" + bas.get("value"));
                        System.out.println("key:" + bas.get("key"));
                    }


                    bb.close();
                    reader.close();
                    in.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(mUrl);
    }
}
