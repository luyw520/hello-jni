package com.example.lu.dragger;

import android.os.Bundle;

import com.example.lu.R;
import com.example.lu.base.BaseActivity;
import com.lu.aoplib.LogUtil;

public class DraggerTestActivity extends BaseActivity<DraggerTestPresenter> {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aop);
        setTitle("DraggerTestActivity");
//        DaggerMainComponent.create().inject(this);
       LogUtil.d(mPersenter.hello());

    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_aop;
    }
}
