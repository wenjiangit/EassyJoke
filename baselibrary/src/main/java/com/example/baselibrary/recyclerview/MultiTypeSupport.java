package com.example.baselibrary.recyclerview;

/**
 * 多类型支持
 * Created by douliu on 2017/5/12.
 */

public interface MultiTypeSupport<T> {

    //可以通过具体的数据内容或位置来确定布局,设计较为巧妙
    int getTypeAndLayoutId(T item,int position);
}
