package com.example.hankhe.mytool.AppManager;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hankhe.mytool.R;
import com.example.hankhe.mytool.activityUtil.AppManager_Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * Created by hankhe on 2017/4/17.
 */
public class TotalApp extends Fragment implements AdapterView.OnItemClickListener{
    private ListView mListView = null;
    private List<AppInfo> mAppInfo = null;
    private Context mContext;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View totalApp = inflater.inflate(R.layout.totalapp_layout, container, false);

        mListView = (ListView) totalApp.findViewById(R.id.total_list);
        mAppInfo = new ArrayList<AppInfo>();
        mContext = totalApp.getContext();


        getAppList(mContext);

        AppInfoAdapter adapter = new AppInfoAdapter(mContext,mAppInfo);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);

        String totalNumber = String.valueOf(mAppInfo.size());
        return totalApp;

    }

    private int getAppMemSize(Context context){
        int memorySize = 0;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得系统里正在运行的所有进程
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessesList) {
            // 进程ID号
            int pid = runningAppProcessInfo.pid;
            // 用户ID
            int uid = runningAppProcessInfo.uid;
            // 进程名
            String processName = runningAppProcessInfo.processName;
            // 占用的内存
            int[] pids = new int[] {pid};
            Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(pids);
            memorySize = memoryInfo[0].dalvikPrivateDirty;

            System.out.println("processName="+processName+",pid="+pid+",uid="+uid+",memorySize="+memorySize+"kb");
        }
        return memorySize;
    }
    /**
     * getAppList
     * @param context
     * **/
    private void getAppList(Context context){
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> appList = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : appList){

            AppInfo appInfo = new AppInfo();
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setAppName((String) packageInfo.applicationInfo.loadLabel(pm));
            appInfo.setAppVersion(packageInfo.versionName);
            appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(pm));
            appInfo.setAppIntent(pm.getLaunchIntentForPackage(packageInfo.packageName));
//            Debug.MemoryInfo[] memoryInfo = pm.get
            mAppInfo.add(appInfo);

        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        startActivity(mAppInfo.get(position).setAppIntent());

    }
}
