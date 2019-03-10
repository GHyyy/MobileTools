package com.example.hankhe.mytool.LogUtil;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by hankhe on 2017/2/21.
 */
public class GetLogcat extends AsyncTask<String,String,String> {

    public void getLog() throws IOException {
        Process process;
        BufferedReader bf;
        String cmd = "logcat -d -v time -t 1000 *:V";
        process = Runtime.getRuntime().exec(cmd);

        InputStream input = process.getInputStream();
        InputStreamReader inReader = new InputStreamReader(input);
        bf = new BufferedReader(inReader);

        String line ="";
        while ((line = bf.readLine())!=null){
//            System.out.println("++@@++" + line);
            Log.v("++@@++",line);
        }
        bf.close();
        inReader.close();
        input.close();
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            getLog();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
