package com.example.hankhe.mytool.AppManager.ReadProcess;

/**
 * Created by hankhe on 2017/3/30.
 *
 * Modle 类，存放进程信息
 */
public class ProcessInfo {
    private int pid;
    private int uid;
    private int memSize;
    private String processName;


    public ProcessInfo(){}

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMemSize() {
        return memSize;
    }

    public void setMemSize(int memSize) {
        this.memSize = memSize;
    }

    public String getProcessName() {
        return processName;
    }
    public void setPocessName(String processName) {
        this.processName = processName;
    }
}
