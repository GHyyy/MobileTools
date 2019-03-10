package com.example.hankhe.mytool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hankhe.mytool.Setting_module.About_Acitivity;
import com.example.hankhe.mytool.Setting_module.RewritePopwindow;



/**
 * Created by hankhe on 2016/11/22.
 */
public class SettingLayout extends Fragment implements View.OnClickListener{
    private RewritePopwindow mPopwindow;
    private ImageButton backBtn;
    private TextView shareView;
    private TextView aboutView;
    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.setting_layout,container,false);

        backBtn = (ImageButton) settingLayout.findViewById(R.id.btn_df_back);
        shareView = (TextView) settingLayout.findViewById(R.id.shareId);
        aboutView = (TextView) settingLayout.findViewById(R.id.btn_about);

        shareView.setOnClickListener(this);
        aboutView.setOnClickListener(this);

        mContext = getContext();
        return settingLayout;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.shareId:
                mPopwindow = new RewritePopwindow((Activity) mContext,itemsOnClick);
                mPopwindow.showAtLocation(v,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.btn_about:
                Intent intentAbout = new Intent(mContext,About_Acitivity.class);
                startActivity(intentAbout);
                break;
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            mPopwindow.dismiss();
            mPopwindow.backgroundAlpha((Activity) mContext, 1f);
            switch (v.getId()) {
                case R.id.weixinghaoyou:
                    Toast.makeText(mContext, "微信好友", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pengyouquan:
                    Toast.makeText(mContext, "朋友圈", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.qqhaoyou:
                    Toast.makeText(mContext, "QQ好友", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.qqkongjian:
                    Toast.makeText(mContext, "QQ空间", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    };

}
