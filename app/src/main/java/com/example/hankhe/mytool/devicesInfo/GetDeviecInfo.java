package com.example.hankhe.mytool.devicesInfo;

import android.content.Context;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

/**
 * Created by hankhe on 2016/12/14.
 */
public class GetDeviecInfo {

    //获取MAC地址信息 兼容 Android 6.0及以上
    public String getMacAddress(){
        String address="";
        try {

            List<NetworkInterface> all= Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all){
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] by=nif.getHardwareAddress();

                if (by == null){
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : by){
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0){
                    res1.deleteCharAt(res1.length() -1);
                }
                return  res1.toString();

            }


        } catch (SocketException e) {
            e.printStackTrace();
        }
        return address;
    }
}


