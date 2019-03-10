package com.example.hankhe.mytool.AppManager;

import android.app.ActivityManager;
import android.content.Context;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.os.Debug;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.example.hankhe.mytool.AppManager.ReadProcess.AndroidAppProcess;
import com.example.hankhe.mytool.AppManager.ReadProcess.ProcessManager;
import com.example.hankhe.mytool.AppManager.ReadProcess.AppUtils;
import com.example.hankhe.mytool.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hankhe on 2017/4/17.
 */
public class RunningApp extends Fragment {
    private Context mContext;
    private ListView mListView = null;
    private List<AppInfo> mAppInfo = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View runningApp = inflater.inflate(R.layout.totalapp_layout,container,false);

        mContext = getContext();
        mListView = (ListView) runningApp.findViewById(R.id.total_list);
        mAppInfo = new ArrayList<AppInfo>();



//        getRunnAppList(mContext);
//        getRunnappByTask(mContext);
//        AppInfoAdapter adapter = new AppInfoAdapter(mContext,mAppInfo);
//        mListView.setAdapter(adapter);
        try {

            getAndroidProcess(mContext);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        AppInfoAdapter adapter = new AppInfoAdapter(mContext,mAppInfo);
        mListView.setAdapter(adapter);
        System.out.println("总共：" + mAppInfo.size());
        return runningApp;
    }
    /**
     * getRunnAppList
     * Android 5.0 以下
     * @param context
     * **/
    private void getRunnAppList(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = context.getPackageManager();
        List<ActivityManager.RunningAppProcessInfo> appList = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : appList){

            AppInfo appInfo = new AppInfo();
            appInfo.setPackageName(processInfo.processName);
            mAppInfo.add(appInfo);

        }
    }

    /**
     * 兼容Android 5.0 +
     * getAndroidProcess
     * @param context
     * **/
    public void getAndroidProcess(Context context) throws PackageManager.NameNotFoundException {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = context.getPackageManager();
        AppUtils proutils = new AppUtils(context);
        List<AndroidAppProcess> listInfo = ProcessManager.getRunningAppProcesses();
        for (AndroidAppProcess info : listInfo) {
            ApplicationInfo app = proutils.getApplicationInfo(info.name);
            // 过滤自己当前的应用
            if (app == null || context.getPackageName().equals(app.packageName)) {
                continue;
            }
            // 过滤系统的应用
            if ((app.flags & app.FLAG_SYSTEM) > 0) {
                continue;
            }

             String pkgName = app.packageName;
             PackageInfo packageInfo = pm.getPackageInfo(pkgName,0);
             String appVersion = packageInfo.versionName;

            AppInfo appInfo = new AppInfo();
            appInfo.setAppIcon(app.loadIcon(pm));
            appInfo.setAppName(app.loadLabel(pm).toString());

            appInfo.setPackageName(pkgName);

            appInfo.setAppVersion(appVersion);
            int[] myMempid = new int[] { info.pid };
            Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(myMempid);
            double memSize = memoryInfo[0].dalvikPrivateDirty / 1024.0;
            int temp = (int) (memSize * 100);
            memSize = temp / 100.0;
            appInfo.setAppMenSize((long) memSize);

            mAppInfo.add(appInfo);
        }
    }
}
