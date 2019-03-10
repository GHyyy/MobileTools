package com.example.hankhe.mytool;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.hankhe.mytool.activityUtil.AddData_Activity;
import com.example.hankhe.mytool.activityUtil.DeviceInfo_Activity;
import com.example.hankhe.mytool.activityUtil.AppManager_Activity;
import com.example.hankhe.mytool.activityUtil.Ping_Activity;
import com.example.hankhe.mytool.activityUtil.Network_Activity;
import com.example.hankhe.mytool.activityUtil.Post_Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hankhe on 2016/11/22.
 */
public class ToolsFragment extends Fragment {

    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private int[] icon = {R.drawable.tool_device_info,R.drawable.tool_wifi, R.drawable.tool_broadcast,
            R.drawable.tool_clean_data,R.drawable.tool_file_observer,R.drawable.tool_app_manager};
    private String[] icon_Name = {"设备信息", "网络诊断", "Ping", "Http请求","应用管理","添加数据"};

    private SimpleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View toolsLayout = inflater.inflate(R.layout.tools_layout, container, false);


        gridView = (GridView) toolsLayout.findViewById(R.id.gridview);
        dataList = new ArrayList<>();

        adapter = new SimpleAdapter(this.getActivity(),
                getData(),
                R.layout.gd_item_layout,
                new String[]{"image", "text"},
                new int[]{R.id.ItemImage, R.id.ItemText});

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //监听Activity
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent df = new Intent(parent.getContext(), DeviceInfo_Activity.class);
                        startActivity(df);
                        break;
                    case 1:
                        Intent pt=new Intent(parent.getContext(), Network_Activity.class);
                        startActivity(pt);
                        break;
                    case 2:
                        Intent gt=new Intent(parent.getContext(), Ping_Activity.class);
                        startActivity(gt);
                        break;
                    case 3:
                        Intent nt=new Intent(parent.getContext(), Post_Activity.class);
                        startActivity(nt);
                        break;
                    case 4:
                        Intent appManager=new Intent(parent.getContext(), AppManager_Activity.class);
                        startActivity(appManager);
                        break;
                    case 5:
                        Intent addData=new Intent(parent.getContext(), AddData_Activity.class);
                        startActivity(addData);
                        break;
                    default:
                        break;
                }
            }
        });


        return toolsLayout;

    }

    //数据填充
    private List<Map<String, Object>> getData() {
        for (int i = 0; i < 6; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", icon_Name[i]);
            dataList.add(map);
        }
        return dataList;
    }


}
