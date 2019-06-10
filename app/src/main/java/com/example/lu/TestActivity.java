package com.example.lu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lu.activity.AbstractProcessorActivity;
import com.example.lu.activity.AcrMenuActivity;
import com.example.lu.activity.AopActivity;
import com.example.lu.activity.MainActivity;
import com.example.lu.activity.NotificationActivity;
import com.example.lu.activity.SuperTextViewActivity;
import com.example.lu.activity.TestAidl2Activity;
import com.example.lu.adapter.LuAdapter;
import com.example.lu.adapter.ViewHolder;
import com.example.lu.binder.BookManagerActivity;
import com.example.lu.binderpool.BinderPoolActivity;
import com.example.lu.ble.BleActivity;
import com.example.lu.dragger.DraggerTestActivity;
import com.example.lu.messenger.MessengerActivity;
import com.example.lu.socket.TCPClientActivity;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {


    ListView listView;
    List<ActivityInfo> activityInfoList=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        listView= (ListView) findViewById(R.id.listView);

        activityInfoList.add(new ActivityInfo("AndFix示例",MainActivity.class));
        activityInfoList.add(new ActivityInfo("Aidl示例",TestAidl2Activity.class));
        activityInfoList.add(new ActivityInfo("ArcMenu示例",AcrMenuActivity.class));
        activityInfoList.add(new ActivityInfo("NotificationActivity示例",NotificationActivity.class));
        activityInfoList.add(new ActivityInfo("aidl callback",BookManagerActivity.class));
        activityInfoList.add(new ActivityInfo("BinderPool",BinderPoolActivity.class));
        activityInfoList.add(new ActivityInfo("Messenger",MessengerActivity.class));
        activityInfoList.add(new ActivityInfo("TCPClientActivity",TCPClientActivity.class));
        activityInfoList.add(new ActivityInfo("AbstractProcessor Test",AbstractProcessorActivity.class));
        activityInfoList.add(new ActivityInfo("AopActivity Test",AopActivity.class));
        activityInfoList.add(new ActivityInfo("DraggerTestActivity Test",DraggerTestActivity.class));
        activityInfoList.add(new ActivityInfo("SuperTextViewActivity Test",SuperTextViewActivity.class));
        activityInfoList.add(new ActivityInfo("ble",BleActivity.class));
        LuAdapter<ActivityInfo> adapter=new LuAdapter<ActivityInfo>(this,activityInfoList,android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder helper, ActivityInfo item) {
                helper.setString(android.R.id.text1,item.name);
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(TestActivity.this,activityInfoList.get(position).clazz));
            }
        });

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
    }
    static class ActivityInfo{
        String name;
        Class clazz;
        public ActivityInfo(String name,Class clazz){
            this.name=name;
            this.clazz=clazz;
        }
    }

}
