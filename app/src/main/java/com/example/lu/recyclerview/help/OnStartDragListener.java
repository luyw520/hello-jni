package com.example.lu.recyclerview.help;

import android.support.v7.widget.RecyclerView;

/**
 *@date on 2017/12/6
 *@author pengfeng
 *@describe
 */
public interface OnStartDragListener {

    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);

    void deleteItem(int position);

}