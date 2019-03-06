package com.example.hellojnicallback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hellojnicallback.activity.AbstractProcessorActivity;
import com.example.hellojnicallback.activity.AcrMenuActivity;
import com.example.hellojnicallback.activity.AopActivity;
import com.example.hellojnicallback.activity.MainActivity;
import com.example.hellojnicallback.activity.NotificationActivity;
import com.example.hellojnicallback.activity.SuperTextViewActivity;
import com.example.hellojnicallback.activity.TestAidl2Activity;
import com.example.hellojnicallback.adapter.LuAdapter;
import com.example.hellojnicallback.adapter.ViewHolder;
import com.example.hellojnicallback.binder.BookManagerActivity;
import com.example.hellojnicallback.binderpool.BinderPoolActivity;
import com.example.hellojnicallback.dragger.DraggerTestActivity;
import com.example.hellojnicallback.messenger.MessengerActivity;
import com.example.hellojnicallback.socket.TCPClientActivity;
import com.example.hellojnicallback.tinker.TinkerTestActivity;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {


    ListView listView;
    List<ActivityInfo> activityInfoList=new ArrayList<>();
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
        activityInfoList.add(new ActivityInfo("Tinker Test",TinkerTestActivity.class));
        activityInfoList.add(new ActivityInfo("AbstractProcessor Test",AbstractProcessorActivity.class));
        activityInfoList.add(new ActivityInfo("AopActivity Test",AopActivity.class));
        activityInfoList.add(new ActivityInfo("DraggerTestActivity Test",DraggerTestActivity.class));
        activityInfoList.add(new ActivityInfo("SuperTextViewActivity Test",SuperTextViewActivity.class));
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
