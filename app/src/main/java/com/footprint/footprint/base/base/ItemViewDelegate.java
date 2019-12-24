package com.footprint.footprint.base.base;


/**
 * 实现ViewHolder的一些信心
 */
public interface ItemViewDelegate<T>
{
    //条目的ID
    int getItemViewLayoutId();
    //获取当前该条目是不是你所标记的类型
    boolean isForViewType(T item, int position);

    //相当于ViewHolder的封装
    void convert(ViewHolder holder, T t, int position);

}
