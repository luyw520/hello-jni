package com.example.lu.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class LuLinearLayout extends FrameLayout {
    public LuLinearLayout(Context context) {
        super(context);
        init();
    }

    public LuLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LuLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    Paint paint;
    private void init(){
        bgColor = Color.WHITE;
        setRadius(5);
        setWillNotDraw(false);
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(bgColor);

        path = new Path();
    }
    private float[] radiusArray = { 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f };
    Path path;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawRect(0,0,getWidth(),getHeight(),paint);
//
//
//        path.addRoundRect(0, 0, getWidth(), getHeight(), radiusArray, Path.Direction.CW);
//        canvas.clipPath(path);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(0,0,getWidth(),getHeight(),radius,radius,paint);
        }else{
            canvas.drawRect(0,0,getWidth(),getHeight(),paint);
        }


    }


    private int bgColor=-1;
    public void setRadius(float radius) {
        this.radius = Utils.dp2px(radius, (Activity) getContext());
    }
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }


    private int radius;




}
