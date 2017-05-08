package com.example.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.baselibrary.ioc.ViewUtils;

/**
 * Created by wenjian on 2017/4/30.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView();
        ViewUtils.inject(this);

        initTitle();

        initView();

        initData();

    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化头部
     */
    protected abstract void initTitle();


    /**
     * 设置布局
     */
    protected abstract void setContentView();


    /**
     * 启动Activity
     * @param clazz
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * findViewById 不需要强转
     * @param viewId
     * @param <T> View
     * @return
     */
    protected <T extends View> T findView(@IdRes int viewId) {
        return (T) findViewById(viewId);
    }


    /**
     * 吐司
     * @param text
     */
    protected void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
