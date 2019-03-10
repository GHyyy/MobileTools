package com.example.hankhe.mytool.CheckNetwork;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by hankhe on 2017/2/16.
 */
public class CheckToNetwork {
    private Context mContext;

    public CheckToNetwork(Context mContext){
        this.mContext = mContext;
    }
    public boolean checkNetworkState(){
        boolean flag = false;
        //连接管理对象
        ConnectivityManager mcM= (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(mcM.getActiveNetworkInfo() != null){
            flag=mcM.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }
}
