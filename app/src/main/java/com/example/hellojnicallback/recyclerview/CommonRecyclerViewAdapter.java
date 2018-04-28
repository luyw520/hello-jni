package com.example.hellojnicallback.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;

import com.example.hellojnicallback.recyclerview.base.CommonRecyclerViewHolder;
import com.example.hellojnicallback.recyclerview.base.ItemViewDelegateForRV;

import java.util.List;


/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonRecyclerViewAdapter<T> extends MultiItemTypeAdapterForRV<T>
{
    protected Context mContext;
    protected int mLayoutId;
//    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonRecyclerViewAdapter(final Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
        addItemViewDelegate(0,new ItemViewDelegateForRV<T>()
        {

            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public int getViewType( T item, int position)
            {
                return 0;
            }

            @Override
            public void convert(CommonRecyclerViewHolder holder, T t, int position)
            {
                CommonRecyclerViewAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(CommonRecyclerViewHolder holder, T t, int position);

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
}
