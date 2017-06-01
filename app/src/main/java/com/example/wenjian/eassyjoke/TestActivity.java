package com.example.wenjian.eassyjoke;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.baselibrary.ioc.ViewInject;
import com.example.baselibrary.ioc.ViewUtils;
import com.example.baselibrary.recyclerview.CommonRecyclerAdapter;
import com.example.baselibrary.recyclerview.DefaultItemDecoration;
import com.example.baselibrary.recyclerview.GridItemDecoration;
import com.example.baselibrary.recyclerview.ViewHolder;
import com.example.baselibrary.recyclerview.WrapRecyclerAdapter;
import com.example.baselibrary.recyclerview.WrapRecyclerView;
import com.example.framelibrary.base.BaseSkinActivity;
import com.example.framelibrary.base.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestActivity extends BaseSkinActivity {

    @ViewInject(R.id.recycler_view)
    private WrapRecyclerView mRecyclerView;

    private boolean isLinear;
    private List<String> mData;
    private MyRecyclerAdapter mAdapter;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        for (char i = 'A'; i <= 'z'; i++) {
            mData.add(i + "");
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new MyRecyclerAdapter(this,mData);

        mAdapter.setOnItemClickListener( new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                toast("position:" + holder.getLayoutPosition() + mData.get(holder.getLayoutPosition()) + "被点击了");
            }
        });

        mRecyclerView.setAdapter(mAdapter);
      /*  TextView tv = new TextView(this);
        tv.setText("哈哈,我是头部哦!!!");
        mRecyclerView.addHeaderView(tv);

        ImageView iv = new ImageView(this);
        iv.setImageResource(R.mipmap.ic_launcher);
        mRecyclerView.addFooterView(iv);*/

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //上下拖动
                int dragFlags = 0;

                //左右滑动
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    //
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                            | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else {
                    //线性布局只支持上下拖动
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                }

                return makeMovementFlags(dragFlags,swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int targetPosition = target.getAdapterPosition();
                if (fromPosition < targetPosition) {//向下拖动
                    for (int i = fromPosition; i < targetPosition; i++) {
                        Collections.swap(mData, i, i + 1);
                    }
                } else {//向上拖动
                    for (int i = fromPosition; i > targetPosition; i--) {
                        Collections.swap(mData, i, i - 1);
                    }
                }
                mAdapter.notifyItemMoved(fromPosition,targetPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mData.remove(position);
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return super.isLongPressDragEnabled();
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                }
            }

            /**
             * 回到正常状态的回调
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                viewHolder.itemView.setBackgroundColor(0);
                ViewCompat.setTranslationX(viewHolder.itemView, 0);
            }
        });

        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    protected void initView() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://github.com/";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
            }
        }, null);
        queue.add(request);
        queue.start();

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
                            GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
                            mRecyclerView.setLayoutManager(manager);
                        }
                        isLinear = !isLinear;
                    }
                })
                .create();

    }


    private static class MyRecyclerAdapter extends CommonRecyclerAdapter<String>{


        public MyRecyclerAdapter(@NonNull Context context, List<String> data) {
            super(context, data, R.layout.recycler_item);
        }

        @Override
        protected void convert(com.example.baselibrary.recyclerview.ViewHolder holder, String item) {
            holder.setText(R.id.tv_char, item);
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

