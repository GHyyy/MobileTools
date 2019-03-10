package com.example.hankhe.mytool.AppManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by hankhe on 2017/4/19.
 * Model 类，存储App信息
 */
public class AppInfo {
    private String appName;
    private String packageName;
    private String appVersion;
    private Drawable appIcon;
    private long appMenSize;
    private Intent AppIntent;
    private boolean userTask;

    public AppInfo(){}

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void setAppMenSize(long appMenSize) {
        this.appMenSize = appMenSize;
    }

    public long getAppMenSize() {
        return appMenSize;
    }

    public void setAppIntent(Intent appIntent) {
        AppIntent = appIntent;
    }

    public Intent getAppIntent() {
        return AppIntent;
    }

    public void setUserTask(boolean userTask) {
        this.userTask = userTask;
    }

    @Override
    public String toString() {
        return "TaskInfo [icon=" + appIcon + ", name=" + appName + ", packname="
                + packageName + ", memsize=" + appMenSize + ", userTask=" + userTask
                + "]";

    }
}
