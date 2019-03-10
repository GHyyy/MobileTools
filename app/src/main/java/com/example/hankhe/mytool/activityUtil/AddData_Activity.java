package com.example.hankhe.mytool.activityUtil;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.hankhe.mytool.MainActivity;
import com.example.hankhe.mytool.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hankhe on 2017/5/3.
 */
public class AddData_Activity extends Activity {
    private Context mContext;
    private Button writeContaces;
    private TextView tvShow;
    private String[] userName = {"张小明","王良","李飞",};
    private String[] userPhone = {"13895956898","15898986565","15968979989"};
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            for(int i = 0; i < userName.length;i++){
                if (msg.what == i){
                    tvShow.append("Contacts:" + userName[i] + "\t" + userPhone[i] + "\n");
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddata_layout);
        doInit();
    }

    private void doInit(){
        mContext = getApplicationContext();
        writeContaces = (Button) findViewById(R.id.btn_contacts);
        tvShow = (TextView) findViewById(R.id.id_show);
    }

    private void readmContacts(){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    getError();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                for (int i = 0; i < userPhone.length;i++){
                        addNewContacts(userName[i],userPhone[i]);
                        System.out.println(i + i + 1);
                        mHandler.sendEmptyMessage(i);

                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

            }
        }).start();

    }

    /**
     * addNewContacts    write Contacts
     * @param userName
     * @param phoneNum
     * **/
    private void addNewContacts(String userName,String phoneNum){
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = mContext.getContentResolver();
        ContentValues values = new ContentValues();
        long contactId = ContentUris.parseId(resolver.insert(uri, values));

        /* 往 data 中添加数据（要根据前面获取的id号） */
        // 添加姓名
        uri = Uri.parse("content://com.android.contacts/data");
        values.put("raw_contact_id", contactId);
        values.put("mimetype", "vnd.android.cursor.item/name");
        values.put("data2", userName);
        resolver.insert(uri, values);

        // 添加电话
        values.clear();
        values.put("raw_contact_id", contactId);
        values.put("mimetype", "vnd.android.cursor.item/phone_v2");
        values.put("data2", "2");
        values.put("data1", phoneNum);
        resolver.insert(uri, values);

    }

    private void getError() throws InterruptedException, IOException {
        String resault="";
        Process p;
        p=Runtime.getRuntime().exec("  ");

        int status = p.waitFor();
        InputStream input = p.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        StringBuffer bf = new StringBuffer();
        String line="";
        while ((line = in.readLine()) !=null ){
            bf.append(line + "\n");
        }
        System.out.println("Return =========" + bf.toString());
    }


    //btn-back监听
    public void doClick(View v){
        switch (v.getId()){
            case R.id.btn_df_back:
                Intent bc=new Intent(this, MainActivity.class);
                startActivity(bc);
                this.finish();
                break;
            case R.id.btn_contacts:
                tvShow.append("开始添加......" + "\n");
                readmContacts();

//                Toast.makeText(mContext,"添加完成",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
