package com.example.baselibrary.dao;

import java.util.List;

/**
 * Created by douliu on 2017/5/24.
 */

public interface IDaoSupport<T> {

    long insert(T t);

    void insert(List<T> data);

    void delete();

    List<T> queryAll();

    void update();

}
