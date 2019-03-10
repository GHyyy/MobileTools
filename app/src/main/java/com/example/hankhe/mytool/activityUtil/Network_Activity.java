package com.example.hankhe.mytool.activityUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.*;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hankhe.mytool.CheckNetwork.CheckToNetwork;
import com.example.hankhe.mytool.MainActivity;
import com.example.hankhe.mytool.R;
import com.example.hankhe.mytool.devicesInfo.GetDeviecInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Process;

/**
 * Created by hankhe on 2016/12/5.
 */
public class Network_Activity  extends Activity implements OnTouchListener{

    private Button btnWifi,btnNet;
    private TextView tv;
    private Context mcontext;
    private String macAddress;
    private boolean flag =  false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.netwok_layout);
        initView();
    }

    private void initView(){
        btnWifi= (Button) this.findViewById(R.id.btn_wifi);
        btnNet= (Button) findViewById(R.id.btn_netInfo);

        tv= (TextView) findViewById(R.id.tv_show);

        mcontext=getApplicationContext();


        btnWifi.setOnTouchListener(this);
        btnNet.setOnTouchListener(this);

        CheckToNetwork checkToNetwork = new CheckToNetwork(mcontext);
        flag = checkToNetwork.checkNetworkState();
    }

    //btn-back监听
    public void doClick(View v){
//        initTV();
        switch (v.getId()){
            case R.id.btn_df_back:
                Intent bc=new Intent(this, MainActivity.class);
                startActivity(bc);
                this.finish();
                break;
            case R.id.btn_wifi:
                isNetworkType();
                break;
            case R.id.btn_netInfo:
                getNetInfo();
                break;
            default:
                break;
        }
    }

    //btn 点击style
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            v.setBackgroundResource(R.drawable.small_button_pressed);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            v.setBackgroundResource(R.drawable.small_button);
        }
        return false;
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

    //初始化 TextView
    private void initTV(){
        tv.setText("检测中...");
    }

     //@ btn_wifi   判断是网络连接类型：wifi 或  GPRS
    private void isNetworkType(){
        if (!flag){
            Toast.makeText(mcontext, "网络未连接，请连接网络", Toast.LENGTH_SHORT).show();
            tv.setText("网络未连接....");
        }else {
            //连接管理对象
            ConnectivityManager mcM= (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo.State gprs=mcM.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            NetworkInfo.State wifi=mcM.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

            //获取当前连接 wifi 的SSID
            WifiManager wifiManager= (WifiManager) getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo=wifiManager.getConnectionInfo();


            Log.d("wifiInfo",wifiInfo.toString());
            Log.d("SSID",wifiInfo.getBSSID());

            if (gprs == NetworkInfo.State.CONNECTED || gprs == NetworkInfo.State.CONNECTING){
                tv.setText("网络已连接" + "\n"+"网络连接方式：GPRS");
            }

            if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING){
                tv.setText("网络已连接" + "\n" + "\n" + "网络连接方式：wifi" + "\n" + "SSID：" + wifiInfo.getSSID().toString());
            }
        }

    }

    //@ btn_Net 获取网络信息
    private void getNetInfo(){
        if (!flag){
            Toast.makeText(mcontext, "网络未连接，请连接网络", Toast.LENGTH_SHORT).show();
            tv.setText("网络未连接....");
        }else {
            //连接管理对象
            ConnectivityManager mcM = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo.State gprs = mcM.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            NetworkInfo.State wifi = mcM.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

            //获取当前连接 wifi 的SSID
            WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();


            Log.d("wifiInfo", wifiInfo.toString());
            Log.d("SSID", wifiInfo.getBSSID());

            if (gprs == NetworkInfo.State.CONNECTED || gprs == NetworkInfo.State.CONNECTING) {
                tv.setText("网络已连接" + "\n" + "网络连接方式：GPRS");
            }

            if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {

                int ip = wifiInfo.getIpAddress();
                String ipInfo = doIpAddress(ip);
                macAddress = wifiInfo.getMacAddress();
                //Mac地址获取，兼容Android 6.0及以上
                if (macAddress.equals("02:00:00:00:00:00")) {
                    GetDeviecInfo getDeviecInfo = new GetDeviecInfo();
                    macAddress = getDeviecInfo.getMacAddress();
                }

                tv.setText("网络信息" + "\n" + "\n" + "SSID：" + wifiInfo.getSSID().toString()
                        + "\n" + "IP：" + ipInfo + "\n" + "MAC：" + macAddress
                        + "\n" + "LinkSpeed：" + wifiInfo.getLinkSpeed());
            }
        }
    }

    // @ btn_Net For ipAddress
    private String doIpAddress(int i){
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }
}
