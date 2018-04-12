package com.example.hellojnicallback;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.hellojnicallback.log.DebugLog;
import com.example.hellojnicallback.log.KLog;
import com.example.hellojnicallback.log.LogUtil;
import com.lu.aidl.ILuAidlInterface;

public class TestAidlActivity extends AppCompatActivity {
    ILuAidlInterface iLuAidlInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_aidl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ServiceConnection connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TestAidlActivity.class.getSimpleName(),"onServiceConnected");
                iLuAidlInterface=ILuAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TestAidlActivity.class.getSimpleName(),"onServiceDisconnected");
            }
        };
        Intent intent=new Intent("com.example.hellojnicallback.LuService");
//        Intent intent=new Intent(this,LuService.class);
        intent.setPackage(getPackageName());
        bindService(intent,connection,BIND_AUTO_CREATE);

        findViewById(R.id.btnCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    iLuAidlInterface.callFunction("hello AIDL");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(TestAidlActivity.class.getSimpleName(),"iLuAidlInterface.add(1,2):"+iLuAidlInterface.add(1,2));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        LogUtil.d("onCreate");
        KLog.d("onCreate");
        DebugLog.d("onCreate");
    }

}
