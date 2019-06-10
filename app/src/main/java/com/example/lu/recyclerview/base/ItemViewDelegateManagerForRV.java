package com.example.lu.recyclerview.base;

import android.support.v4.util.SparseArrayCompat;


/**
 * Created by zhy on 16/6/22.
 */
public class ItemViewDelegateManagerForRV<T>
{
    SparseArrayCompat<ItemViewDelegateForRV<T>> delegates = new SparseArrayCompat();

    public int getItemViewDelegateCount()
    {
        return delegates.size();
    }

//    public ItemViewDelegateManagerForRV<T> addDelegate(ItemViewDelegateForRV<T> delegate)
//    {
//        int viewType = delegates.size();
//        if (delegate != null)
//        {
//            delegates.put(viewType, delegate);
//            viewType++;
//        }
//        return this;
//    }

    public ItemViewDelegateManagerForRV<T> addDelegate(int viewType, ItemViewDelegateForRV<T> delegate)
    {
        if (delegates.get(viewType) != null)
        {
            throw new IllegalArgumentException(
                    "An ItemViewDelegateForRV is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegateForRV is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    public ItemViewDelegateManagerForRV<T> removeDelegate(ItemViewDelegateForRV<T> delegate)
    {
        if (delegate == null)
        {
            throw new NullPointerException("ItemViewDelegateForRV is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0)
        {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public ItemViewDelegateManagerForRV<T> removeDelegate(int itemType)
    {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0)
        {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public int getItemViewType(T item, int position)
    {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--)
        {
            ItemViewDelegateForRV<T> delegate = delegates.valueAt(i);
            if (delegate.getViewType( item, position) == delegates.keyAt(i))
            {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateForRV added that matches position=" + position + " in data source");
    }

    public void convert(CommonRecyclerViewHolder holder, T item, int position)
    {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++)
        {
            ItemViewDelegateForRV<T> delegate = delegates.valueAt(i);

            if (delegate.getViewType( item, position)  == delegates.keyAt(i))
            {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManagerForRV added that matches position=" + position + " in data source");
    }


    public ItemViewDelegateForRV getItemViewDelegate(int viewType)
    {
        return delegates.get(viewType);
    }

    public int getItemViewLayoutId(int viewType)
    {
        return getItemViewDelegate(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(ItemViewDelegateForRV itemViewDelegate)
    {
        return delegates.indexOfValue(itemViewDelegate);
    }
}
