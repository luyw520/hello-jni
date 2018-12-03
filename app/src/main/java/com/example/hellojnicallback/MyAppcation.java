package com.example.hellojnicallback;


import com.tencent.tinker.loader.app.TinkerApplication;

/**
 * Created by Administrator on 2018/4/17 0017.
 */

public class MyAppcation extends TinkerApplication {

    public MyAppcation() {
        super(7, "com.example.hellojnicallback.tinker.SampleApplicationLike" , "com.tencent.tinker.loader.TinkerLoader", false);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
