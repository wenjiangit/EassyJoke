package com.example.wenjian.eassyjoke;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baselibrary.ioc.ViewInject;
import com.example.baselibrary.ioc.ViewUtils;
import com.example.baselibrary.recyclerview.DefaultItemDecoration;
import com.example.framelibrary.base.BaseSkinActivity;
import com.example.framelibrary.base.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseSkinActivity {

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;

    private boolean isLinear;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initData() {
        List<String> data = new ArrayList<>();
        for (char i = 'A'; i < 'z'; i++) {
            data.add(i + "");
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(this));

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("RecyclerView基本使用")
                .setRightText("切换布局")
                .setRightClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isLinear) {
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
                        } else {
                            mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
                        }
                        isLinear = !isLinear;
                    }
                })
                .create();

    }


    private static class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{


        private List<String> mData;

        public MyRecyclerAdapter(List<String> data) {
            mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTvChar.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            @ViewInject(R.id.tv_char)
            public TextView mTvChar;

            public ViewHolder(View itemView) {
                super(itemView);
                ViewUtils.inject(itemView,this);
            }
        }
    }


    public static class LinearItemDecoration extends RecyclerView.ItemDecoration {

        private final Paint mPaint;

        public LinearItemDecoration() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.BLUE);
            mPaint.setStyle(Paint.Style.FILL);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int position = parent.getChildLayoutPosition(view);

            if (position != 0) {
                outRect.top = 5;
            }
        }


        @Override
        public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(canvas, parent, state);

            Rect rect = new Rect();
            rect.left = parent.getPaddingLeft();
            rect.right = parent.getWidth() - parent.getPaddingRight();
            int count = parent.getChildCount();
            for (int i = 1; i < count; i++) {
                View child = parent.getChildAt(i);
                rect.bottom = child.getTop();
                rect.top = child.getTop() - 5;
                canvas.drawRect(rect, mPaint);
            }



        }
    }
}

