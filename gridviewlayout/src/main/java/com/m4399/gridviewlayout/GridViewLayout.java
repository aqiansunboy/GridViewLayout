package com.m4399.gridviewlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Name: m4399_GameCenter
 * File Name:    GridViewLayout.java
 * ClassName:    GridViewLayout
 *
 * Description: Grid视图容器（适用于少量数据源, 无复用需求的表格布局需求）.
 *
 * @author Chaoqian Wu
 * @date 2015年09月10日 下午5:11
 *
 * Copyright (c) 2015年, 4399 Network CO.ltd. All Rights Reserved.
 */
public class GridViewLayout extends LinearLayout implements View.OnClickListener
{
    public static final int GRID_LINE_MODE_SINGLE = 0;   // 单行模式
    public static final int GRID_LINE_MODE_MULTI = 1;    // 多行多列模式
    public static final int GRID_LINE_MODE_SINGLE_COLUMN = 2; // 单列模式

    private ArrayList<View> mViews;

    private int mHorizontalSpacing = 0;

    private int mVerticalSpacing = 0;

    private int mNumColumns = 0;

    private int mNumRows = 0;

    private int mGridLineMode = GRID_LINE_MODE_SINGLE;

    private GridViewLayoutAdapter mAdapter;

    private OnItemClickListener mOnItemClickListener;

    public GridViewLayout(Context context)
    {
        super(context);
    }

    public GridViewLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray typeArrays = context.obtainStyledAttributes(attrs, R.styleable.GridViewLayout);

        mHorizontalSpacing = typeArrays.getDimensionPixelOffset(
                R.styleable.GridViewLayout_horizontalSpacing, 0);

        mVerticalSpacing = typeArrays.getDimensionPixelOffset(
                R.styleable.GridViewLayout_verticalSpacing, 0);

        mNumColumns = typeArrays.getInt(R.styleable.GridViewLayout_numColumns, 0);

        mNumRows = typeArrays.getInt(R.styleable.GridViewLayout_numRows, 0);

        mGridLineMode = typeArrays.getInt(R.styleable.GridViewLayout_gridLineMode, GRID_LINE_MODE_SINGLE);
        // 如果是多行多列的，必须是垂直方向
        if (mGridLineMode == GRID_LINE_MODE_MULTI)
        {
            this.setOrientation(LinearLayout.VERTICAL);
        }
        // 如果是单行的，必须是水平方向
        else if (mGridLineMode == GRID_LINE_MODE_SINGLE)
        {
            this.setOrientation(LinearLayout.HORIZONTAL);
        }
        // 如果是单列的，必须是垂直方向
        else if (mGridLineMode == GRID_LINE_MODE_SINGLE_COLUMN)
        {
            this.setOrientation(LinearLayout.VERTICAL);
        }

        typeArrays.recycle();
    }

    public void setAdapter(GridViewLayoutAdapter adapter)
    {
        this.mAdapter = adapter;
        this.mAdapter.registerAdapterDataObserver(new GridViewLayoutDataObserver());
        this.requestLayoutOnChanged();
    }

    public GridViewLayoutAdapter getAdapter()
    {
        return mAdapter;
    }

    public void setHorizontalSpacing(int horizontalSpacing)
    {
        this.mHorizontalSpacing = horizontalSpacing;
    }

    public void setVerticalSpacing(int verticalSpacing)
    {
        this.mVerticalSpacing = verticalSpacing;
    }

    public void setNumColumns(int numColumns)
    {
        this.mNumColumns = numColumns;
    }

    public void setNumRows(int numRows)
    {
        this.mNumRows = numRows;
    }

    public void setGridLineMode(int gridLineMode)
    {
        this.mGridLineMode = gridLineMode;

        // 如果是多行多列的，必须是垂直方向
        if (mGridLineMode == GRID_LINE_MODE_MULTI)
        {
            this.setOrientation(LinearLayout.VERTICAL);
        }
        // 如果是单行的，必须是水平方向
        else if (mGridLineMode == GRID_LINE_MODE_SINGLE)
        {
            this.setOrientation(LinearLayout.HORIZONTAL);
        }
        // 如果是单列的，必须是垂直方向
        else if (mGridLineMode == GRID_LINE_MODE_SINGLE_COLUMN)
        {
            this.setOrientation(LinearLayout.VERTICAL);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mOnItemClickListener = listener;
    }

    public View getChildView(int position)
    {
        if (mViews != null && position < mViews.size())
        {
            return mViews.get(position);
        }
        return null;
    }

    @Override
    protected void onDetachedFromWindow()
    {
        if (mAdapter != null)
        {
            mAdapter.unregisterAdapterDataObserver();
        }

        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View view)
    {
        if (mOnItemClickListener != null)
        {
            mOnItemClickListener.onItemClick(this, view, (Integer) view.getTag());
        }
    }

    @SuppressWarnings("unchecked")
    private void requestSingleLineLayoutOnChanged()
    {
        if (mAdapter == null)
        {
            throw new IllegalArgumentException("You need one adaper instance.Please call setAdapter().");
        }

        if (mViews == null)
        {
            mViews = new ArrayList<>();
        }

        if (mViews.size() > 0)
        {
            this.removeAllViews();
            mViews.clear();
        }

        if (mNumColumns == 0)
        {
            mNumColumns = mAdapter.getData().size();
        }

        for (int i = 0;i < Math.min(mNumColumns, mAdapter.getData().size());i++)
        {
            GridViewLayoutViewHolder childView = mAdapter.createView(this);

            mViews.add(childView.getItemView());

            LayoutParams params = new LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;

            if (i > 0)
            {
                params.setMargins(mHorizontalSpacing, 0, 0, 0);
            }

            addView(childView.getItemView(), params);

            mAdapter.onBindView(childView, i);
            childView.getItemView().setTag(i);
            childView.getItemView().setOnClickListener(this);
        }
    }

    @SuppressWarnings("unchecked")
    private void requestSingleColumnLayoutOnChanged()
    {
        if (mAdapter == null)
        {
            throw new IllegalArgumentException("You need one adaper instance.Please call setAdapter().");
        }

        if (mViews == null)
        {
            mViews = new ArrayList<>();
        }

        if (mViews.size() > 0)
        {
            this.removeAllViews();
            mViews.clear();
        }

        if (mNumRows == 0)
        {
            mNumRows = mAdapter.getData().size();
        }

        for (int i = 0;i < Math.min(mNumRows, mAdapter.getData().size());i++)
        {
            GridViewLayoutViewHolder childView = mAdapter.createView(this);

            mViews.add(childView.getItemView());

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
            params.weight = 1;

            if (i > 0)
            {
                params.setMargins(0, mVerticalSpacing, 0, 0);
            }

            addView(childView.getItemView(), params);

            mAdapter.onBindView(childView, i);
            childView.getItemView().setTag(i);
            childView.getItemView().setOnClickListener(this);
        }
    }

    @SuppressWarnings("unchecked")
    private void requestMultiLineLayoutOnChanged()
    {
        if (mAdapter == null)
        {
            throw new IllegalArgumentException("You need one adaper instance.Please call setAdapter().");
        }

        if (mNumColumns == 0)
        {
            throw new IllegalArgumentException("numColumns must not be zero in multiLine mode.Please set it by app:numColumns or setNumColumns()");
        }

        if (mViews == null)
        {
            mViews = new ArrayList<>();
        }

        if (mViews.size() > 0)
        {
            this.removeAllViews();
            mViews.clear();
        }

        int itemCount = mAdapter.getItemCount();

        // 如果没有设置行数
        if (itemCount > 0 && mNumRows == 0)
        {
            if (itemCount % mNumColumns != 0)
            {
                mNumRows = (itemCount / mNumColumns) + 1;
            }
            else
            {
                mNumRows = itemCount / mNumColumns;
            }
        }

        for (int rowIndex = 0; rowIndex < mNumRows; rowIndex++)
        {

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LayoutParams layoutParams = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if (rowIndex < mNumRows-1)
            {
                layoutParams.setMargins(0, 0, 0, mVerticalSpacing);
            }
            layoutParams.gravity = Gravity.CENTER;
            linearLayout.setLayoutParams(layoutParams);

            // 当前行布局
            for (int position = 0; position < mNumColumns; position++)
            {
                GridViewLayoutViewHolder childView = mAdapter.createView(this);

                mViews.add(childView.getItemView());

                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                       ViewGroup.LayoutParams.WRAP_CONTENT);
                params.weight = 1;

                if ((position % mNumColumns) > 0)
                {
                    params.setMargins(mHorizontalSpacing, 0, 0, 0);
                    linearLayout.addView(childView.getItemView(), params);
                }
                else
                {
                    linearLayout.addView(childView.getItemView(), params);
                }

                int dataIndex = position + rowIndex*mNumColumns;

                if (dataIndex < itemCount)
                {
                    mAdapter.onBindView(childView, dataIndex);
                    childView.getItemView().setTag(dataIndex);
                    childView.getItemView().setOnClickListener(this);
                }
                else
                {
                    childView.getItemView().setVisibility(INVISIBLE);
                }

            }

            // 添加行
            addView(linearLayout);
        }


    }

    private void requestLayoutOnChanged()
    {
        switch (mGridLineMode)
        {
            case GRID_LINE_MODE_SINGLE:
                requestSingleLineLayoutOnChanged();
                break;
            case GRID_LINE_MODE_SINGLE_COLUMN:
                requestSingleColumnLayoutOnChanged();
                break;
            case GRID_LINE_MODE_MULTI:
                requestMultiLineLayoutOnChanged();
                break;
        }
    }

    private class GridViewLayoutDataObserver
    {
        protected void onChanged()
        {
            GridViewLayout.this.requestLayoutOnChanged();
        }
    }

    public static abstract class GridViewLayoutViewHolder
    {
        private View itemView;
        private Context context;
        public View getItemView()
        {
            return itemView;
        }

        public Context getContext()
        {
            return context;
        }

        public GridViewLayoutViewHolder(Context context, View itemView)
        {
            if (itemView == null)
            {
                throw new IllegalArgumentException("itemView may not be null");
            }
            else
            {
                this.itemView = itemView;
            }

            this.context = context;

            initView();
        }

        protected <T extends View> T findViewById(int id)
        {
            return (T) itemView.findViewById(id);
        }

        protected abstract void initView();

        public GridViewLayoutViewHolder setText(TextView view, CharSequence text)
        {
            if (view == null)
            {
                return this;
            }

            view.setText(text);
            return this;
        }

        public GridViewLayoutViewHolder setVisible(View view, boolean visible)
        {
            if (view == null)
            {
                return this;
            }

            int visibility = visible ? View.VISIBLE : View.INVISIBLE;
            view.setVisibility(visibility);
            return this;
        }

    }

    public static abstract class GridViewLayoutAdapter<D,V extends GridViewLayoutViewHolder>
    {
        private Context mContext;

        private List<D> mData;

        private GridViewLayoutDataObserver mObserver;

        public GridViewLayoutAdapter(Context context)
        {
            this(context,null);
        }

        public GridViewLayoutAdapter(Context context, List<D> data)
        {
            this.mContext = context;
            this.mData = (data == null ? new ArrayList<D>() : data);
        }

        public Context getContext()
        {
            return mContext;
        }

        public void registerAdapterDataObserver(GridViewLayoutDataObserver observer)
        {
            this.mObserver = observer;
        }

        public void unregisterAdapterDataObserver()
        {
            this.mObserver = null;
        }

        public List<D> getData()
        {
            return mData;
        }

        public final V createView(ViewGroup parent)
        {
            View itemView = LayoutInflater.from(mContext)
                                     .inflate(getItemLayoutID(), parent, false);
            return onCreateView(itemView);
        }

        protected abstract int getItemLayoutID();

        protected abstract V onCreateView(View itemView);

        protected abstract void onBindView(V view, int position);

        public void replaceAll(List<D> data)
        {
            if (mData != data)
            {
                mData.clear();
                mData.addAll(data);
            }
            notifyDataSetChanged();
        }

        public void notifyDataSetChanged()
        {
            if (mObserver == null)
            {
                return;
            }

            mObserver.onChanged();
        }

        public int getItemCount()
        {
            return mData.size();
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(ViewGroup parent, View view, int position);
    }
}
