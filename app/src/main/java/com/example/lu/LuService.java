package com.example.lu;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.lu.activity.TestAidlActivity;
import com.lu.aidl.ILuAidlInterface;

public class LuService extends Service {
    public LuService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TestAidlActivity.class.getSimpleName(),"onBind");
        return mIBinder;
    }
    private IBinder mIBinder=new ILuAidlInterface.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void callFunction(String str) throws RemoteException {
            Log.d(LuService.class.getSimpleName(),"callFunction方法被调用"+str);
        }

        @Override
        public int add(int a, int b) throws RemoteException {
            Log.d(LuService.class.getSimpleName(),"add方法被调用");
            return a+b;
        }
    };
}
