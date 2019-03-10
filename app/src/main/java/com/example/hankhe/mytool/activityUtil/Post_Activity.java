package com.example.hankhe.mytool.activityUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hankhe.mytool.MainActivity;
import com.example.hankhe.mytool.OkHttp_Utils.DoHttp;
import com.example.hankhe.mytool.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hankhe on 2016/12/5.
 */
public class Post_Activity extends Activity {
    private TextView tvResponse,tvRequest;
    private Spinner mSpinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private String getPan,getUga,apiLog;
    private String mParams;
    private String url;
    private Context mContext;
    private HashMap<String,String> mHttpParams;
    private DoHttp doHttp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        initView();
    }

    private void initView(){
        mSpinner= (Spinner) findViewById(R.id.post_sp);
        tvResponse= (TextView) findViewById(R.id.tv_response);
        tvRequest= (TextView) findViewById(R.id.tv_request);
        mContext = getApplicationContext();
        doHttp = new DoHttp(tvRequest,tvResponse);
        //接口地址
        url="http://www.baidu.cn/test-api/api/timestamp";
//        url="http://www.baidu.cn/test-api/api/feedback";
        mParams="sign=757db5bf2549d0125260e07200564584&region=&timestamp=1487917353&network=WIFI&version_name=1.1.7-debug&country=&contact=&description=testtesttesttetsggsyg&expect_server_compress=0&screen_size=1080%231776&inner_package_name=com.tcl.launcherpro&os_version=5.0.2&language=zh_CN&user_info_id=&os_version_code=21&model=\n";

        getPan="getPackageNames";
        getUga="getUpgradeApkList";
        apiLog="api/cgs/log";

        //数据
        data_list= new ArrayList<String>();
        data_list.add(getPan);
        data_list.add(getUga);
        data_list.add(apiLog);

        //适配器
        arr_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //加载适配器
        mSpinner.setAdapter(arr_adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosesIt=parent.getItemAtPosition(position).toString();
                Log.i("result===当前选择的url", chosesIt + position);

//                Volley_utils volley_utils = new Volley_utils(url + chosesIt,tv_response,tvRequest);
//                volley_utils.doPost();
                doChooseApi(position);
//                doHttp.doPost(url,mParams);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    /**
     * 选择的Server API
     * @param mId 选的接口id
     * **/
    private void doChooseApi(int mId){
        switch (mId){
            case 0:
                tvResponse.setText(doHttp.doGet(url));
                tvRequest.setText("Server：www.baidu.cn" + '\n' + "Http协议：http" + '\n'+ "Http Method：GET" + '\n'+ "接口根地址：test-api/api/timestamp");
                break;
            case 1:
                break;
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

    public class MyThread implements Runnable{
        @Override
        public void run() {
            Message  message = Message.obtain();


        }
    }
    //屏蔽back键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                return true;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
