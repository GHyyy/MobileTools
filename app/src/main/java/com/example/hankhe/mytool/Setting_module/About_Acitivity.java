package com.example.hankhe.mytool.Setting_module;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;


import com.example.hankhe.mytool.MainActivity;
import com.example.hankhe.mytool.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hankhe on 2017/6/22.
 */

public class About_Acitivity extends ListActivity implements View.OnClickListener{
    private Context mContext;
    private ImageButton imgBack;
    private Button testBtn;
    private static  final  String TAG = "TestTool";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_about_layout);
        doInit();
    }

    private void  doInit(){
        mContext = getApplicationContext();
        imgBack = (ImageButton) findViewById(R.id.btn_df_back);
        imgBack.setOnClickListener(this);
        testBtn = (Button) findViewById(R.id.btn_dl);

        SimpleAdapter adapter=new SimpleAdapter(this,
                getData(),
                R.layout.setting_about_item,
                new String[]{"title"},
                new int[]{R.id.tvTitle});
        setListAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_df_back:
                Intent intentSet = new Intent(mContext,MainActivity.class);
                startActivity(intentSet);
                break;

            case R.id.btn_dl:
                getWifi();
                break;
        }

    }

    private List<Map<String,Object>> getData(){

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();

        map.put("title","隐私声明");
        list.add(map);

        return list;
    }

    private void getWifi(){
        String proHost = android.net.Proxy.getDefaultHost();
        int proPort = android.net.Proxy.getDefaultPort();
        Log.v(TAG,proHost);
        Log.v(TAG, String.valueOf(proPort));

    }
}
