package com.example.framelibrary.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baselibrary.base.BaseActivity;
import com.example.framelibrary.R;

/**
 * Created by wenjian on 2017/4/30.
 */

public abstract class BaseSkinActivity extends BaseActivity {
    // TODO: 2017/4/30 插件换肤


    @Override
    protected void setContentView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View rootView = inflater.inflate(R.layout.activity_base, null);
        ViewGroup contentView = (ViewGroup) rootView.findViewById(R.id.fl_content);
        View inflate = inflater.inflate(bindLayoutId(), contentView, false);
        contentView.addView(inflate);

        setContentView(rootView);
    }

    protected abstract int bindLayoutId();

}
