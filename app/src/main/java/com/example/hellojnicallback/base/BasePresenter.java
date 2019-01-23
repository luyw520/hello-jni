package com.example.hellojnicallback.base;


import java.lang.ref.WeakReference;

/**
 */

public abstract class BasePresenter<V extends IBaseView> {


    protected WeakReference<V> mWeak;


    /**
     *绑定的View接口
     * @param v
     */
    public void attachView(V v){
        mWeak=new WeakReference<>(v);
    }

    /**
     * 当前View是否存在，或者没被销毁
     * @return
     */
    protected boolean isAttachView(){
        return mWeak!=null&&mWeak.get()!=null;
    }

    /**
     * 解绑当前View
     * @return
     */
    public void detachView(){
        if (mWeak!=null){
            mWeak.clear();
            mWeak=null;
        }
    }

}
