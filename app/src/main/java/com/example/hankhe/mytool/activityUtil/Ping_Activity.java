package com.example.hankhe.mytool.activityUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hankhe.mytool.CheckNetwork.CheckPing;
import com.example.hankhe.mytool.MainActivity;
import com.example.hankhe.mytool.R;

/**
 * Created by hankhe on 2016/12/5.
 */
public class Ping_Activity extends Activity implements View.OnTouchListener{
    private TextView tvRequest;
    private EditText edIp;
    private String mUrl;
    private Context mContext;
    private Button btnPing;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            CheckPing checkPing = new CheckPing(tvRequest,mUrl);
            checkPing.execute();
            mHandler.postDelayed(this,1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ping_layout);

        tvRequest = (TextView) findViewById(R.id.tv_showAnr);
        edIp = (EditText) findViewById(R.id.ed_address);
        mContext = getApplicationContext();

        edIp.setText("www.baidu.com");
        btnPing = (Button) findViewById(R.id.btn_reader);
        btnPing.setOnTouchListener(this);
    }

    //btn-back监听
    public void doClick(View v) throws Exception {
        switch (v.getId()) {
            case R.id.btn_df_back:
                Intent bc = new Intent(this, MainActivity.class);
                startActivity(bc);
                this.finish();
                break;
            case R.id.btn_reader:
                checkNetworkState();
//                GetLogcat getLogcat = new GetLogcat();
//                getLogcat.execute();
                break;
            default:
                break;
        }
    }
    //判断网络是否连接
    private boolean checkNetworkState(){
        boolean flag = false;
        //连接管理对象
        ConnectivityManager mcM= (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(mcM.getActiveNetworkInfo() != null){
            flag=mcM.getActiveNetworkInfo().isAvailable();
        }

        if (!flag){
            Toast.makeText(mContext,"网络未连接，请连接网络",Toast.LENGTH_SHORT).show();
        }else {
            doPing(mContext);
        }

        return flag;
    }
    private void doPing(Context context){
        mUrl =edIp.getText().toString();
        if (mUrl.length() == 0){
            Toast.makeText(context,"请输入目标IP或域名",Toast.LENGTH_SHORT).show();
        }else {
            Log.i("+++murl+++",mUrl);
            CheckPing checkPing = new CheckPing(tvRequest,mUrl);
            checkPing.execute();
        }

    }
    private void timerTask(){
        mHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                CheckPing checkPing = new CheckPing(tvRequest,mUrl);
                checkPing.execute();
                mHandler.postDelayed(this,1000);
            }
        };
    }
        //屏蔽back键r
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);

    }

    //btn 点击style
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            v.setBackgroundResource(R.drawable.btn_post_pressed);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            v.setBackgroundResource(R.drawable.btn_post);
        }
        return false;
    }
}


