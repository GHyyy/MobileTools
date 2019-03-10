package com.example.hankhe.mytool.CheckNetwork;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hankhe on 2017/2/9.
 */
public class CheckPing extends AsyncTask<String,String,String> {
    private TextView tvRequest;
    private String mUrl;

    public CheckPing(TextView tvRequest,String mUrl){
        this.tvRequest = tvRequest;
        this.mUrl = mUrl;
    }


    //@ btn_Ky Ping命令执行
    public String doPing(String str) throws IOException {
        String resault="";
        Process p;
        //ping -c 3 -w 100  -c指ping的次数，3 指 ping 3次，-w 100 以秒为单位，指定超时时间为100秒
        try {

            p=Runtime.getRuntime().exec("ping -c 3 -w 100" + str);
            Log.i("+++ Str +++++++", str);
            int status = p.waitFor();
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer bf = new StringBuffer();
            String line="";
            while ((line = in.readLine()) !=null ){
                bf.append(line + "\n");
            }
            System.out.println("Return =========" + bf.toString());

            if (status == 0){
                resault=bf.toString() + "\n" + "*** 通信正常 ***";
            }else {
                resault="\n" + "*** 通信异常 ***";
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resault;
    }

    @Override
    protected String doInBackground(String... params) {
        String s="";
        try {
                s= doPing( "\t" + mUrl );

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("ping+++++++++++++++++++",s);
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
            tvRequest.setText(s);
    }
}
