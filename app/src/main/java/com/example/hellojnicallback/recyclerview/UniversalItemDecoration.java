package com.example.hellojnicallback.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by yu on 2017/3/19.
 * 万能分割
 * 使用此类  item的tag会被占用  如果外部使用会造成乱
 * 外部可以使用 item.setTag(key,obj)
 */
public abstract class UniversalItemDecoration extends RecyclerView.ItemDecoration {

    private Map<Integer, Decoration> decorations = new HashMap<>();

    private static final String TAG = "UniversalItemDecoration";

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {

            final View child = parent.getChildAt(i);
            //获取在getItemOffsets存起来的position
            int position = string2Int(child.getTag().toString(), 0);
            Decoration decoration = decorations.get(position);

            if (decoration == null) continue;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();

            //view的上下左右包括 Margin
            int bottom = child.getBottom() + layoutParams.bottomMargin;
            int left = child.getLeft() - layoutParams.leftMargin;
            int right = child.getRight() + layoutParams.rightMargin;
            int top = child.getTop() - layoutParams.topMargin;

            //下面的
            if (decoration.bottom != 0)
                decoration.drawItemOffsets(c, left - decoration.left, bottom, right + decoration.right, bottom + decoration.bottom,2);
            //上面的
            if (decoration.top != 0)
                decoration.drawItemOffsets(c, left - decoration.left, top - decoration.top, right + decoration.right, top,1);
            //左边的
            if (decoration.left != 0)
                decoration.drawItemOffsets(c, left - decoration.left, top, left, bottom,3);
            //右边的
            if (decoration.right != 0)
                decoration.drawItemOffsets(c, right, top, right + decoration.right, bottom,4);

        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //获取position
        int position = parent.getChildAdapterPosition(view);
        view.setTag(position);

        //获取调用者返回的Decoration
        Decoration decoration = getItemOffsets(position);

        if (decoration != null) {
            //偏移量设置给item
            outRect.set(decoration.left, decoration.top, decoration.right, decoration.bottom);

        }
        //存起来在onDraw用
        decorations.put(position, decoration);

    }


    /***
     * 需调用者返回分割线对象  上下左右间距值 和颜色值
     * @param position
     * @return
     */
    public abstract Decoration getItemOffsets(int position);

    /**
     * 分割线
     */
    public abstract static class Decoration {

        public int left, right, top, bottom;

        /**
         * 根据偏移量设定的 当前的线在界面中的坐标
         *
         * @param leftZ
         * @param topZ
         * @param rightZ
         * @param bottomZ
         * @param loacation 1:top,2:bottom,3:left,4:right
         */
        public abstract void drawItemOffsets(Canvas c, int leftZ, int topZ, int rightZ, int bottomZ, int loacation);

    }

    public static class ColorDecoration extends Decoration {

        private Paint mPaint;
        public int decorationColor = Color.BLACK;//分割线的颜色
        public int splitItemColor = -1;//分割条的颜色
        public int topLeftPadding = 0;//分割线的上左内距
        public int bottomLeftPadding = -1;//分割线的下左内距
        public int itemBgColor = -1; //padding部分的线条颜色
        public Context mContext;
        public boolean isFirstPosition;

        public ColorDecoration(Context context) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.FILL);
            this.mContext = context;
        }

        @Override
        public void drawItemOffsets(Canvas c, int leftZ, int topZ, int rightZ, int bottomZ, int loacation) {
//            if(splitItemColor != -1){
//                mPaint.setColor(splitItemColor);
//                c.drawRect(topLeftPadding, topZ-mContext.getResources().getDimensionPixelOffset(1), rightZ, bottomZ, mPaint);//画分割条
//                mPaint.setColor(decorationColor);
//                if(!isFirstPosition && loacation == 1) {
//                     c.drawRect(topLeftPadding, topZ, rightZ, topZ+mContext.getResources().getDimensionPixelOffset(R.dimen.x1), mPaint);//上分割线
//                }
//                c.drawRect(topLeftPadding, topZ + top - mContext.getResources().getDimensionPixelOffset(R.dimen.x1), rightZ, bottomZ, mPaint);//下分割线
//            }else {
//                mPaint.setColor(decorationColor);
//                if(loacation ==2){
//                    c.drawRect(bottomLeftPadding, topZ, rightZ, bottomZ, mPaint);
//                }else{
//                    c.drawRect(topLeftPadding, topZ, rightZ, bottomZ, mPaint);
//                }
//            }
//            if(topLeftPadding !=0 && loacation!=2){
//                mPaint.setColor(itemBgColor);
//                c.drawRect(0, topZ, topLeftPadding, bottomZ, mPaint);
//            }
        }

    }


    public static int string2Int(String s, int defValue) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return defValue;
        }


    }



}