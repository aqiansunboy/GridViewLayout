package com.m4399.gridviewlayout.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.m4399.gridviewlayout.GridViewLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridViewLayout gridViewLayout1 = (GridViewLayout) findViewById(R.id.singleLineGrid);
        gridViewLayout1.setAdapter(new TextGridAdaper(this));

        GridViewLayout gridViewLayout2 = (GridViewLayout) findViewById(R.id.singleColumnGrid);
        gridViewLayout2.setAdapter(new TextGridAdaper(this));

        GridViewLayout gridViewLayout3 = (GridViewLayout) findViewById(R.id.multiRowColumnGrid);
        gridViewLayout3.setAdapter(new TextGridAdaper(this));

        // 填充数据
        List<String> datas = new ArrayList<>();
        datas.add("1");
        datas.add("2");
        datas.add("3");
        datas.add("4");

        gridViewLayout1.getAdapter().replaceAll(datas);

        gridViewLayout2.getAdapter().replaceAll(datas);

        gridViewLayout3.getAdapter().replaceAll(datas);
    }

    private static class TextGridViewHolder extends GridViewLayout.GridViewLayoutViewHolder
    {
        private TextView mTextView;

        public TextGridViewHolder(Context context, View itemView)
        {
            super(context, itemView);
        }

        @Override
        protected void initView()
        {
            mTextView = findViewById(R.id.gridCellTextView);
        }

        public void bindView(String text)
        {
            mTextView.setText(text);
        }
    }

    private static class TextGridAdaper extends GridViewLayout.GridViewLayoutAdapter<String, TextGridViewHolder>
    {

        public TextGridAdaper(Context context)
        {
            super(context);
        }

        @Override
        protected int getItemLayoutID()
        {
            return R.layout.viewholder_gridview;
        }

        @Override
        protected TextGridViewHolder onCreateView(View itemView)
        {
            return new TextGridViewHolder(getContext(), itemView);
        }

        @Override
        protected void onBindView(TextGridViewHolder view, int position)
        {
            view.bindView(getData().get(position));
        }
    }
}
