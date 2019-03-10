package com.example.hankhe.mytool.AppManager.ReadProcess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hankhe.mytool.R;

import java.util.List;

/**
 * Created by hankhe on 2017/3/30.
 * ProcessInfo  listview的适配器
 */
public class ProcessInfoAdapter extends BaseAdapter{
    private List<ProcessInfo> mlistProcessInfo = null;
    LayoutInflater infater = null;

    public ProcessInfoAdapter(Context context,  List<ProcessInfo> apps){
        infater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mlistProcessInfo = apps;
    }

    public int getCount(){
        System.out.println("size" + mlistProcessInfo.size());
        return mlistProcessInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mlistProcessInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        System.out.println("getView at " + position);
        View view = null;
        ViewHolder holder = null;

        if (convertview == null || convertview.getTag() == null) {
            view = infater.inflate(R.layout.process_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else{
            view = convertview ;
            holder = (ViewHolder) convertview.getTag() ;
        }
        ProcessInfo processInfo = (ProcessInfo) getItem(position);
        holder.tvPID.setText(processInfo.getPid() +"");
        holder.tvUID.setText(processInfo.getUid() +"") ;
        holder.tvProcessMemSize.setText(processInfo.getMemSize()+"KB");
        holder.tvProcessName.setText(processInfo.getProcessName());

        return view;
    }

    class ViewHolder {
        TextView tvPID ;             //进程ID
        TextView tvUID ;             //用户ID
        TextView tvProcessMemSize ;  //进程占用内存大小
        TextView tvProcessName ;   // 进程名
        public ViewHolder(View view) {
            this.tvPID = (TextView)view.findViewById(R.id.tvProcessPID) ;
            this.tvUID = (TextView) view.findViewById(R.id.tvProcessUID);
            this.tvProcessMemSize = (TextView) view.findViewById(R.id.tvProcessMemSize);
            this.tvProcessName = (TextView)view.findViewById(R.id.tvProcessName) ;
        }
    }

}
