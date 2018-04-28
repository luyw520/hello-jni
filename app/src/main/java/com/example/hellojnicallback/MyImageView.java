package com.example.hellojnicallback;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.hellojnicallback.log.DebugLog;

public class MyImageView extends ImageView {
    private Camera mCamera = new Camera();

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float lastX;
    private float w;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                w = getMeasuredWidth();
                break;
            case MotionEvent.ACTION_MOVE:
                degrees = (event.getX() - lastX) / w * 180;
                invalidate();
                break;
        }
        return true;
    }

    private float degrees;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        final Matrix matrix = getMatrix();

        mCamera.save();
//            if (mReverse) {
//                camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
//            } else {
//                camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
//            }
//            camera.setLocation(centerX,centerY,-8);
        DebugLog.d("degrees:" + degrees);
        mCamera.rotateY(degrees);
//            camera.rotateX(degrees);
//            camera.rotateZ(degrees);
        mCamera.getMatrix(matrix);
        mCamera.restore();

        matrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
        matrix.postTranslate(getWidth() / 2, getHeight() / 2);
//        canvas.setMatrix(matrix);
    }

}