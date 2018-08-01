package com.example.hellojnicallback;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import com.example.hellojnicallback.log.DebugLog;

public class MainArcMenu {

    public final static int DURATIONMILLIS = 200;

    /**
     * 起始点横坐标
     */
    private int startX;
    /**
     * 起始点纵坐标
     */
    private int startY;
    /**
     * 是否正在执行动画标识
     */
    private boolean isAnimating;
    /**
     * 当前Item索引
     */
    private int currentIndex;

    /**
     * 是否处于显示状态
     */
    public boolean isShown;

    /**
     * 初始化坐标
     *
     * @param activity
     */
    public void init(Activity activity) {
        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        DebugLog.d("width:" + width + ",height:" + height);
        startX = width / 2;
        startY = height / 2;
    }
    boolean isSupportBloodBressure;
    public void setSupportBloodBressure(boolean isSupportBloodBressure){
        this.isSupportBloodBressure=isSupportBloodBressure;
    }
    /**
     * 获取旋转动画
     *
     * @param fromDegrees    开始角度
     * @param toDegrees      结束角度
     * @param durationMillis 动画时长
     * @param count          执行次数（+1）
     * @return
     */
    public Animation getRotateAnimation(float fromDegrees, float toDegrees,
                                        int durationMillis, int count) {
        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(durationMillis);
        rotate.setRepeatCount(count);
        rotate.setFillAfter(true);
        return rotate;
    }

    boolean isFirst=true;
    /**
     * 打开动画
     *
     * @param viewgroup      扇形菜单父视图
     * @param durationMillis 每个Item动画执行毫秒数
     */

    public void openMenuAnimation(ViewGroup viewgroup, int durationMillis) {
        if (isAnimating) {
            return;
        }
        isAnimating = true;

        final int length = viewgroup.getChildCount() - 1;
        View bottom_layout;
//        boolean isSupportBloodBressure=false;
        View cicle;
        if (isSupportBloodBressure){
            bottom_layout= viewgroup.findViewById(R.id.rlView2);
            cicle=viewgroup.findViewById(R.id.iv_blood);
        }else {
            bottom_layout= viewgroup.findViewById(R.id.rlView);
            cicle=viewgroup.findViewById(R.id.iv_cicle);
        }

        bottom_layout.setVisibility(View.VISIBLE);
        for (int i = 0; i < length; i++) {

            final View v = viewgroup.getChildAt(i);
            v.setVisibility(View.VISIBLE);
            float fromXDelta = 0;
            float fromYDelta = 0;
            fromXDelta=bottom_layout.getLeft()+cicle.getLeft()-v.getRight()+cicle.getWidth();
            fromYDelta=bottom_layout.getTop()+cicle.getTop()-v.getBottom()+cicle.getHeight();
            isFirst=false;
            DebugLog.d("bottom_layout.getLeft():"+bottom_layout.getLeft()+",cicle.getLeft():"+cicle.getLeft()+",v.getRight():"+v.getRight());
            DebugLog.d("bottom_layout.getTop():"+bottom_layout.getTop()+",cicle.getTop():"+cicle.getTop()+",v.getBottom():"+v.getBottom());
            AnimationSet set = new AnimationSet(true);
            TranslateAnimation animation = new TranslateAnimation(fromXDelta, 0, fromYDelta, 0);
            animation.setDuration(durationMillis);

            set.setInterpolator(new AccelerateDecelerateInterpolator());//开始和结束的时候慢，中间加速
            set.addAnimation(getRotateAnimation(360, 0, 2 * DURATIONMILLIS, 0));
            set.addAnimation(animation);
            set.setFillAfter(true);
            set.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    currentIndex++;
                    if (currentIndex == length) {
                        isAnimating = false;
                        currentIndex = 0;
                        isShown = true;
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            v.startAnimation(set);

        }
    }


    /**
     * 关闭动画
     *
     * @param viewgroup      扇形菜单父视图
     * @param durationMillis 每个Item动画执行毫秒数
     */
    public void closeMenuAnimation(ViewGroup viewgroup, int durationMillis) {
        if (isAnimating) {
            return;
        }
        isAnimating = true;

        final int lenght = viewgroup.getChildCount() - 1;
        View bottom_layout;
//        boolean isSupportBloodBressure=false;
        View cicle;
        if (isSupportBloodBressure){
            bottom_layout = viewgroup.findViewById(R.id.rlView2);
            cicle=viewgroup.findViewById(R.id.iv_blood);
        }else {
            bottom_layout = viewgroup.findViewById(R.id.rlView);
            cicle=viewgroup.findViewById(R.id.iv_cicle);
        }
//        View bottom_layout = viewgroup.findViewById(R.id.rlView);
        currentIndex = 0;
        for (int i = 0; i < lenght; i++) {
            final View v =
                    viewgroup.getChildAt(i);
            v.clearAnimation();
            float toXDelta = 0;
            float toYDelta = 0;

            toXDelta=bottom_layout.getLeft()+cicle.getLeft()-v.getRight()+cicle.getWidth();
            toYDelta=bottom_layout.getTop()+cicle.getTop()-v.getBottom()+cicle.getHeight();

            DebugLog.d("i:" + i + ",toYDelta:" + toYDelta + ",toXDelta:" + toXDelta);
            final TranslateAnimation animation = new TranslateAnimation(0,
                    toXDelta, 0, toYDelta);

            AnimationSet set = new AnimationSet(true);
            animation.setFillAfter(true);
//            animation.setInterpolator(new DecelerateInterpolator());// 回弹
            animation.setDuration(durationMillis);

            Animation rotate = getRotateAnimation(0, 360, durationMillis, 0);
            set.addAnimation(rotate);
            set.addAnimation(animation);
            set.setDuration(durationMillis);


            set.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    currentIndex++;
                    DebugLog.d("currentIndex:" + currentIndex);
                    v.setVisibility(View.GONE);
                    if (currentIndex == lenght) {
                        isAnimating = false;
                        currentIndex = 0;
                        isShown = false;
                    }

                }
            });
            v.startAnimation(set);

        }

    }

    public void clear() {
        isAnimating = false;
        currentIndex = 0;
        isShown = false;
    }

    /**
     * 扇形动画是否处于执行中
     *
     * @return
     */
    public boolean isAnimating() {
        return isAnimating;
    }

    /**
     * 扇形菜单是否处于显示中
     *
     * @return
     */
    public boolean isShown() {
        return isShown;
    }


}