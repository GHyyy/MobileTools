package com.example.hankhe.mytool.AppManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hankhe.mytool.R;

import java.util.List;

/**
 * Created by hankhe on 2017/4/20.
 */
public class AppInfoAdapter extends BaseAdapter{
    private List<AppInfo> mListAppInfo = null;
    LayoutInflater inflater = null;

    public AppInfoAdapter (Context context,List<AppInfo> apps){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListAppInfo = apps;
    }
    @Override
    public int getCount() {
        return mListAppInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mListAppInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;

        if (convertView == null || convertView.getTag() == null) {
            view = inflater.inflate(R.layout.totalapp_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else{
            view = convertView ;
            holder = (ViewHolder) convertView.getTag() ;
        }
        AppInfo appInfo = (AppInfo) getItem(position);
        holder.appName.setText(appInfo.getAppName());
        holder.packageName.setText(appInfo.getPackageName()) ;
        holder.appMenSize.setText(appInfo.getAppMenSize() + "MB");
        holder.appVersion.setText(appInfo.getAppVersion());
        holder.appIcon.setImageDrawable(appInfo.getAppIcon());

        return view;
    }

    class ViewHolder {
        TextView appName ;
        TextView packageName ;
        TextView appVersion ;
        TextView appMenSize ;
        ImageView appIcon;
        public ViewHolder(View view) {

            this.appName = (TextView) view.findViewById(R.id.id_appName);
            this.packageName = (TextView) view.findViewById(R.id.id_packageName);
            this.appVersion = (TextView) view.findViewById(R.id.id_appVersion);
            this.appMenSize = (TextView) view.findViewById(R.id.id_appMemSize);
            this.appIcon = (ImageView) view.findViewById(R.id.id_appIcon);
        }
    }
}
