package com.example.lu.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import javax.inject.Inject;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {


    @Inject
    protected P mPersenter;
    protected final static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE=100;
    protected final static int ACCESS_FINE_LOCATION_REQUEST_CODE=100;
    protected Activity activity;
    protected Activity mActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResID());
        activity=this;
        mActivity=this;
        initData();
    }
    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 布局资源文件
     * @return 布局资源文件
     */
    public abstract int getLayoutResID();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPersenter!=null) {
            mPersenter.detachView();
        }
    }
}
