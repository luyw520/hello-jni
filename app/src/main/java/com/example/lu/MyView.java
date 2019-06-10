package com.example.lu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.lu.log.DebugLog;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class MyView extends View {


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
////        setOnTouchListener(new OnTouchListener() {
////            @Override
////            public boolean onTouch(View v, MotionEvent event) {
////                Log.d("MyView","onTouch-----调用Action:"+event.getAction()+",返回:"+false);
////                return false;
////            }
////        });
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getContext(),"onClick", Toast.LENGTH_LONG).show();
//                getContext().startActivity(new Intent(getContext(),Main2Activity.class));
//            }
//        });
////        getParent().requestDisallowInterceptTouchEvent(true);
//    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
////        Log.d("MyView","dispatchTouchEvent----调用");
////        getParent().requestDisallowInterceptTouchEvent(true);
//
//        Log.d("MyView","dispatchTouchEvent-------调用Action:"+event.getAction());
//        boolean dispatchTouchEvent=super.dispatchTouchEvent(event);
////        dispatchTouchEvent=true;
//
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
//        Log.d("MyView","super.dispatchTouchEvent----调用后:"+dispatchTouchEvent);
//        return dispatchTouchEvent;
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d("MyView","onTouchEvent-------调用Action:"+event.getAction());
//        boolean dispatchTouchEvent=super.onTouchEvent(event);
////        dispatchTouchEvent=true;
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                dispatchTouchEvent=true;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                dispatchTouchEvent=true;
//                break;
//            case MotionEvent.ACTION_UP:
//                dispatchTouchEvent=false;
//                break;
//        }
//        Log.d("MyView","super.onTouchEvent----调用后:"+dispatchTouchEvent);
//        return dispatchTouchEvent;
//    }

    Paint paint=new Paint();
    @Override
    protected void onDraw(Canvas canvas) {
        DebugLog.d("onDraw方法被调用了。。。。");
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
        paint.setColor(Color.BLACK);
        canvas.drawText("我是自定义控件",50,50,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DebugLog.d("onMeasure方法被调用了。。。。");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        DebugLog.d("draw方法被调用了。。。。");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        DebugLog.d("onLayout方法被调用了。。。。");
    }
}
