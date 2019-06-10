package com.example.lu.activity;;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.lu.R;
import com.example.lu.log.DebugLog;

public class Main3dActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3d);
    }


    public void rotate(View view){
//        int height=view.getHeight();
//        int width=view.getMeasuredWidth();
//        Rotate3dAnimation rotate3dAnimation=new Rotate3dAnimation(0,360,width/2,height/2,1,true);
//        rotate3dAnimation.initialize(width,height,width,height);
//        view.setAnimation(rotate3dAnimation);


        View v=findViewById(R.id.img);
        final int width = v.getWidth();
        final int height = v.getHeight();
//        final int width = getWindowManager().getDefaultDisplay().getWidth();
//        final int height = getWindowManager().getDefaultDisplay().getHeight();
        Rotate3dAnimation animation = new Rotate3dAnimation(0, 360, width/2, height/2,0, false);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(2000);
        animation.setFillAfter(true);
        findViewById(R.id.img).startAnimation(animation);



//        animation.start();
//        findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RotateAnimation rotateAnimation= new RotateAnimation(0,360,width/2,height/2);
//                rotateAnimation.setDuration(2000);
//                v.startAnimation(rotateAnimation);
//            }
//        });
        findViewById(R.id.img).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
    }



    public class Rotate3dAnimation extends Animation {
        private final float mFromDegrees;
        private final float mToDegrees;
        private final float mCenterX;
        private final float mCenterY;
        private final float mDepthZ;
        private final boolean mReverse;
        private Camera mCamera;

        public Rotate3dAnimation(float fromDegrees, float toDegrees,
                                 float centerX, float centerY, float depthZ, boolean reverse) {
            mFromDegrees = fromDegrees;
            mToDegrees = toDegrees;
            mCenterX = centerX;
            mCenterY = centerY;
            mDepthZ = depthZ;
            mReverse = reverse;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float fromDegrees = mFromDegrees;

            float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);
            DebugLog.d("interpolatedTime:"+interpolatedTime+",degrees:"+degrees);
            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;

            final Matrix matrix = t.getMatrix();

            camera.save();
//            if (mReverse) {
//                camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
//            } else {
//                camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
//            }
//            camera.setLocation(centerX,centerY,-8);
            camera.rotateY(degrees);
//            camera.rotateX(degrees);
//            camera.rotateZ(degrees);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }
}
