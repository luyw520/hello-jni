package com.example.lu.recyclerview.base;


/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegateForRV<T>
{

    int getItemViewLayoutId();

    int getViewType(T item, int position);

    void convert(CommonRecyclerViewHolder holder, T t, int position);

}
