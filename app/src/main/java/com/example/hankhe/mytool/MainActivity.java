package com.example.hankhe.mytool;



import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;



import android.widget.ImageView;

import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;




public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private SettingLayout settingFrg;
    private ToolsFragment toolsFrg;
    private View settingLayout;
    private View toolsLayout;
    private TextView toolsText;
    private TextView settingText;
    private ImageView toolsImg;
    private ImageView settingImg;

    private long lastClickTime = 0;

    private static boolean isExit =  false;
    private static Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        setTabSelection(0);
    }

    public void initView() {

        //对应Fragment的contacts界面初始化
        toolsLayout = findViewById(R.id.tools_layout);
        settingLayout = findViewById(R.id.setting_layout);

        //在tab布局上显示的对应标题
        toolsText = (TextView) findViewById(R.id.tools_text);
        settingText = (TextView) findViewById(R.id.setting_text);
        toolsImg = (ImageView) findViewById(R.id.tools_image);
        settingImg = (ImageView) findViewById(R.id.setting_image);
        toolsLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        restTabImage();
        switch (v.getId()) {

            case R.id.tools_layout:
                setTabSelection(0);
//                doGridView();
                break;
            case R.id.setting_layout:
                setTabSelection(1);
                break;
        }
    }

    //selected
    private void setTabSelection(int index) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);

        switch (index) {
            case 0:

                toolsImg.setImageResource(R.drawable.tab_tool_pressed);
                toolsText.setTextColor(this.getResources().getColor(R.color.dodgerblue));

                if (toolsFrg == null) {
                    toolsFrg = new ToolsFragment();
                    transaction.add(R.id.content, toolsFrg);
                } else {
                    transaction.show(toolsFrg);
                }
                break;

            case 1:
                settingImg.setImageResource(R.drawable.tab_setting_pressed);
                settingText.setTextColor(this.getResources().getColor(R.color.dodgerblue));

                if (settingFrg == null) {
                    settingFrg = new SettingLayout();
                    transaction.add(R.id.content, settingFrg);
                } else {
                    transaction.show(settingFrg);
                }
                break;
        }
        transaction.commit();
    }

    //把所有的Fragment 设置为 隐藏状态
    private void hideFragment(FragmentTransaction transaction) {
        if (toolsFrg != null) {
            transaction.hide(toolsFrg);
        }
        if (settingFrg != null) {
            transaction.hide(settingFrg);
        }
    }

    // Default image
    private void restTabImage() {

        toolsImg.setImageResource(R.drawable.tab_tool);
        settingImg.setImageResource(R.drawable.tab_setting);

        toolsText.setTextColor(this.getResources().getColor(R.color.tools));
        settingText.setTextColor(this.getResources().getColor(R.color.tools));
    }


    //屏蔽物理back键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK){
            exitApp();
            return  true;
        }
        return super.onKeyDown(keyCode, event);

    }
    //连续点击2次退出应用
    private void exitApp(){
        if(!isExit){
            isExit = true;
            Toast.makeText(getApplication(),"再按一次后退出程序",Toast.LENGTH_SHORT).show();
            //延迟发送更改状态
            mHandler.sendEmptyMessageDelayed(0,2000);
        }else {
            Log.e("tag","exit application");
            this.finish();
        }
    }

    //退出事件处理

    @Override
    public void onBackPressed() {
        if (lastClickTime <=0){
            Toast.makeText(this,"再按一次退出键退出应用",Toast.LENGTH_SHORT).show();
            lastClickTime = System.currentTimeMillis();
        }else {
            long currentClickTime = System.currentTimeMillis();
            if (currentClickTime - lastClickTime < 1000){
                finish();
            }else {
                Toast.makeText(this,"再按一次退出键退出应用",Toast.LENGTH_SHORT).show();
                lastClickTime = currentClickTime;
            }
        }
    }
}

