package com.example.hankhe.mytool.devicesInfo;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

import com.example.hankhe.mytool.AppManager.ReadProcess.AndroidAppProcess;
import com.example.hankhe.mytool.AppManager.ReadProcess.ProcessManager;

import java.util.List;

/**
 * Created by hankhe on 2017/4/28.
 */
public class GetAppSize {

    private void getAppMem(Context context){
        ActivityManager pm = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<AndroidAppProcess> listInfo = ProcessManager.getRunningAppProcessInfo();
////        int[] myMempid = new int[] { info.pid };
//        Debug.MemoryInfo[] memoryInfo = pm.getProcessMemoryInfo(myMempid);
//        double memSize = memoryInfo[0].dalvikPrivateDirty / 1024.0;
//        int temp = (int) (memSize * 100);
//        memSize = temp / 100.0;
    }
}
