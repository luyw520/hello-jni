package com.example.hellojnicallback.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;
    private int spanCount = 0;
    private boolean includeEdge;

    public SpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    public SpacingItemDecoration(int spacing,int spanCount, boolean includeEdge) {
        this.spacing = spacing;
        this.spanCount = spanCount;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column; // item column
        if(spanCount != 0){
            column = position % spanCount; // item column
            if (includeEdge) {
                outRect.left = spacing - column * spacing;
                outRect.right = (column + 1) * spacing ;

                if (spanCount != 0 && position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing;
                outRect.right = spacing - (column + 1) * spacing;
                if (spanCount != 0 && position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }else {
            outRect.left = spacing;
            outRect.right = spacing;
            outRect.bottom = spacing;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = spacing;
            } else {
                outRect.top = 0;
            }
        }
    }
}