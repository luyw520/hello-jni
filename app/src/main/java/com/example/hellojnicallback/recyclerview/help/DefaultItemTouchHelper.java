package com.example.hellojnicallback.recyclerview.help;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by pengfeng on 2017/12/6.
 * Describe
 */

public class DefaultItemTouchHelper extends ItemTouchHelper {

    DefaultItemTouchHelperCallback itemTouchHelpCallback;
    public DefaultItemTouchHelper(DefaultItemTouchHelperCallback callback) {
        super(callback);
        itemTouchHelpCallback = callback;
    }
    /**
     * 设置是否可以被拖拽
     *
     * @param canDrag 是true，否false
     */
    public void setDragEnable(boolean canDrag) {
        itemTouchHelpCallback.setDragEnable(canDrag);
    }

    /**
     * 设置是否可以被滑动
     *
     * @param canSwipe 是true，否false
     */
    public void setSwipeEnable(boolean canSwipe) {
        itemTouchHelpCallback.setSwipeEnable(canSwipe);
    }

    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        if(itemTouchHelpCallback.isLongPressDragEnabled()){
            startDrag(viewHolder);
        }
    }
}
