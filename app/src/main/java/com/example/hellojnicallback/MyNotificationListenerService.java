package com.example.hellojnicallback;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.example.hellojnicallback.log.DebugLog;

import java.lang.reflect.Field;


@SuppressLint("OverrideAbstract")
public class MyNotificationListenerService extends NotificationListenerService {
    public MyNotificationListenerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        DebugLog.d("onBind.....");
        return super.onBind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    Handler handler=new Handler();

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        DebugLog.d(sbn.toString());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getField();
//                handler.postDelayed(this,500);
            }
        },500);
    }
    public void getField(){
        try {
            Field mWrapper=getClass().getSuperclass().getDeclaredField("mWrapper");
           Object param=mWrapper.get(this);
            if (param!=null){
                DebugLog.d(mWrapper.toString());
            }else{
                DebugLog.d("mWrapper Field is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        for (Field field:NotificationListenerService.class.getDeclaredFields()){
//            DebugLog.d(field.toString());
//        }

    }
    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
