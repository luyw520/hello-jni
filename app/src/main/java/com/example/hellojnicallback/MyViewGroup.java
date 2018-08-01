package com.example.hellojnicallback;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.hellojnicallback.log.DebugLog;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class MyViewGroup extends FrameLayout {
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        Log.d("MyViewGroup","dispatchTouchEvent----调用");
//        boolean dispatchTouchEvent=super.dispatchTouchEvent(event);
//        dispatchTouchEvent=true;
////
////
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                dispatchTouchEvent=true;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                dispatchTouchEvent=false;
//                break;
//            case MotionEvent.ACTION_UP:
//                dispatchTouchEvent=false;
//                break;
//        }
//        Log.d("MyViewGroup","super.dispatchTouchEvent----调用后:"+dispatchTouchEvent);
//        return dispatchTouchEvent;
//    }
//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d("MyViewGroup","onTouch-----调用Action:"+event.getAction()+",返回:"+false);
//                return false;
//            }
//        });
//    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d("MyViewGroup","onTouchEvent-----调用Action:"+event.getAction());
//        boolean onTouchEvent=super.onTouchEvent(event);
//        Log.d("MyViewGroup","super.onTouchEvent(event)-----调用后Action:"+event.getAction()+",onTouchEvent:"+onTouchEvent);
//        return onTouchEvent;
////        return super.onTouchEvent(event);
//    }
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        Log.d("MyViewGroup","onInterceptTouchEvent-------调用Action:"+event.getAction());
//        boolean onInterceptTouchEvent=super.onInterceptTouchEvent(event);
//        onInterceptTouchEvent=false;
//
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
////                onInterceptTouchEvent=false;
//                break;
////            case MotionEvent.ACTION_MOVE:
////
////                onInterceptTouchEvent=false;
////                break;
////            case MotionEvent.ACTION_UP:
////                onInterceptTouchEvent=true;
////                break;
//        }
//        Log.d("MyViewGroup","super.onInterceptTouchEvent-------调用后Action:"+event.getAction()+",onInterceptTouchEvent:"+onInterceptTouchEvent);
//        return onInterceptTouchEvent;
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        View view=getChildAt(0);
//        view.layout(l,t,r,b);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DebugLog.d("onDraw方法被调用了。。。。");
    }

    @Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    DebugLog.d("onMeasure方法被调用了。。。。");
}

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        DebugLog.d("onLayout方法被调用了。。。。");
    }
}
