package com.example.hankhe.mytool.activityUtil;



import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.hankhe.mytool.CheckNetwork.CheckToNetwork;
import com.example.hankhe.mytool.MainActivity;
import com.example.hankhe.mytool.R;
import com.example.hankhe.mytool.devicesInfo.GetDeviecInfo;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by hankhe on 2016/12/1.
 */
public class DeviceInfo_Activity extends ListActivity {

    private String mtype,imei,androidV,ipInfo,macInfo,deviceWH,totalMemory,availMemory;
    private Context mcontext;
    private int h,w;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.deviceinfo_layout);
        super.onCreate(savedInstanceState);

        initView();


    }
    
    private void initView(){
        mcontext=getApplicationContext();

        getDeviceInfo();
        getDeviceHW();
        getMemoryInfo();

        boolean flag = false;
        CheckToNetwork checkToNetwork = new CheckToNetwork(mcontext);
        flag = checkToNetwork.checkNetworkState();
        if(!flag){
            Toast.makeText(mcontext, "网络未连接，请连接网络", Toast.LENGTH_SHORT).show();
        }else {
            getNetInfo();
        }

        SimpleAdapter adapter=new SimpleAdapter(this,
                getData(),
                R.layout.deviceinfo_item_layout,
                new String[]{"title","info"},
                new int[]{R.id.tv_title,R.id.tv_info});
        setListAdapter(adapter);


    }

    private List<Map<String, Object>> getData() {


        List<Map<String,Object>> list=new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("title","设备型号");
        map.put("info", mtype);
        list.add(map);

        map = new HashMap<>();
        map.put("title","Android版本");
        map.put("info", androidV);
        list.add(map);

        map = new HashMap<>();
        map.put("title","分辨率");
        map.put("info", deviceWH);
        list.add(map);

        map = new HashMap<>();
        map.put("title","IP地址");
        map.put("info", ipInfo);
        list.add(map);

        map = new HashMap<>();
        map.put("title","MAC地址");
        map.put("info",macInfo);
        list.add(map);

        map = new HashMap<>();
        map.put("title","IMEI");
        map.put("info",imei);
        list.add(map);

        map = new HashMap<>();
        map.put("title","总内存");
        map.put("info",totalMemory);
        list.add(map);

        map = new HashMap<>();
        map.put("title","剩余内存");
        map.put("info",availMemory);
        list.add(map);


        return list;
    }

    //btn-back监听
    public void doClick(View v){
        switch (v.getId()){
            case R.id.btn_df_back:
                Intent bc=new Intent(this, MainActivity.class);
                startActivity(bc);
                this.finish();
                break;

            default:
                break;
        }
    }

    //屏蔽back键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                return true;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    //获取 设备型号、IMEI号
    private void getDeviceInfo(){
        //获取手机IMEI、型号
        TelephonyManager tm= (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        mtype=Build.MODEL;
        imei=tm.getDeviceId();

        //获取Android系统版本
        androidV= Build.VERSION.RELEASE + "(" + Build.VERSION.SDK  +")";

    }

    //获取网络信息
    private void getNetInfo(){
        //连接管理对象
        ConnectivityManager mcM= (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo.State gprs=mcM.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi=mcM.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

        //获取当前连接 wifi 的SSID
        WifiManager wifiManager= (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo=wifiManager.getConnectionInfo();


        Log.d("wifiInfo", wifiInfo.toString());
        Log.d("SSID",wifiInfo.getBSSID());

        if (gprs == NetworkInfo.State.CONNECTED || gprs == NetworkInfo.State.CONNECTING){

        }

        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING){

            int ip= wifiInfo.getIpAddress();
            ipInfo=doIpAddress(ip);
            macInfo=wifiInfo.getMacAddress();
            //Mac地址获取，兼容Android 6.0及以上
            if (macInfo.equals("02:00:00:00:00:00")){
                GetDeviecInfo getDeviecInfo=new GetDeviecInfo();
                macInfo=getDeviecInfo.getMacAddress();
            }


        }
    }

    //For ipAddress
    private String doIpAddress(int i){
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    //获取device 分辨率
    private void getDeviceHW(){
        WindowManager wd=getWindowManager();
        DisplayMetrics metrics=new DisplayMetrics();
        wd.getDefaultDisplay().getMetrics(metrics);

        h=metrics.heightPixels;
        if (h==1776){
            h=1920;
        }

        w=metrics.widthPixels;

        deviceWH=String.valueOf(w) + "x" + String.valueOf(h);

    }



    //获取 当前可用内存大小
    private String getAvailMemorySize() {
        //获的ActivityManager服务的对象
        ActivityManager mActivityManage= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo=new ActivityManager.MemoryInfo();
        mActivityManage.getMemoryInfo(memoryInfo);

        return Formatter.formatFileSize(getBaseContext(),memoryInfo.availMem);//将获取的内存大小规格化

    }

    /**
     * getMemoryInfo
     * **/
    private String getMemoryInfo(){

         totalMemory = getToalMemory();
         availMemory = getAvailMemory();
        Log.v("++++ getMemoryInfo ++++", "总内存：" + totalMemory + '\n' + "可用内存:" + availMemory);
        return "总内存：" + totalMemory + '\n' + "可用内存：" + availMemory;
    }
    /**
     * getAvailMemory 获取当前可用内存
     * **/
    private String getAvailMemory(){
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(getBaseContext(),mi.availMem);
    }

    /**
     * getToalMemory 获取总内存
     * **/
    private String getToalMemory(){
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return Formatter.formatFileSize(getBaseContext(), initial_memory);// Byte转换为KB或者MB，内存
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void insertDummyContactWrapper() {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
//        insertDummyContact();
    }

}
