package com.example.hankhe.mytool.activityUtil;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.example.hankhe.mytool.AppManager.AppInfo;
import com.example.hankhe.mytool.AppManager.RunningApp;
import com.example.hankhe.mytool.AppManager.TotalApp;
import com.example.hankhe.mytool.MainActivity;
import com.example.hankhe.mytool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hankhe on 2017/4/13.
 */
public class AppManager_Activity extends FragmentActivity implements View.OnClickListener{
    private TextView appTotal,appRuning;
    private TextView totalNumber,runNumber;
    private View totalApp,runningApp;
    private TotalApp totalAppFrg;
    private RunningApp runningAppFrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appmanager_layout);
        doInit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setTabSelection(1);
    }

    private void doInit(){
        appTotal = (TextView) findViewById(R.id.id_appTotal);
        appRuning = (TextView) findViewById(R.id.id_appRun);
        totalNumber = (TextView) findViewById(R.id.id_totalNum);
        runNumber = (TextView) findViewById(R.id.id_runNum);

        totalApp = findViewById(R.id.id_appTotal);
        runningApp = findViewById(R.id.id_appRun);

        totalApp.setOnClickListener(this);
        runningApp.setOnClickListener(this);




    }
    /**
     * SelectionTab
     * @param index
     * **/
    private void setTabSelection(int index){
        FragmentManager fg = getSupportFragmentManager();
        FragmentTransaction transaction = fg.beginTransaction();
        hideFragment(transaction);

        switch(index){
            case 1:
                appTotal.setTextColor(this.getResources().getColor(R.color.dodgerblue));
                appRuning.setTextColor(this.getResources().getColor(R.color.tools));

                if (totalAppFrg == null) {
                    totalAppFrg = new TotalApp();
                    transaction.add(R.id.id_appFrg, totalAppFrg);
                } else {
                    transaction.show(totalAppFrg);
                }
                break;
            case 2:
                appRuning.setTextColor(this.getResources().getColor(R.color.dodgerblue));
                appTotal.setTextColor(this.getResources().getColor(R.color.tools));
                if (runningAppFrg == null) {
                    runningAppFrg = new RunningApp();
                    transaction.add(R.id.id_appFrg, runningAppFrg);
                } else {
                    transaction.show(runningAppFrg);
                }
                break;
        }
        transaction.commit();
    }
    /**
     * hidden Fragment
     * @param transaction
     * **/
    private void hideFragment(FragmentTransaction transaction) {
            if (totalAppFrg != null){
                transaction.hide(totalAppFrg);
            }
            if (runningAppFrg != null){
                transaction.hide(runningAppFrg);
            }
    }

    //btn-back监听
    public void doClick(View v){
        switch (v.getId()){
            case R.id.btn_df_back:
                Intent bc=new Intent(this, MainActivity.class);
                startActivity(bc);
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_appTotal:
                setTabSelection(1);
                break;
            case R.id.id_appRun:
                setTabSelection(2);
                break;
        }
    }
}
