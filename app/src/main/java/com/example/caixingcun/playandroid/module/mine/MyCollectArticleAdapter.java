package com.example.caixingcun.playandroid.module.mine;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.caixingcun.playandroid.R;


import java.util.List;

/**
 * Created by caixingcun on 2018/3/14.
 */

public class MyCollectArticleAdapter extends BaseQuickAdapter<MyCollectArticleBean, BaseViewHolder> {
    public MyCollectArticleAdapter(int layoutResId, @Nullable List<MyCollectArticleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCollectArticleBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_author, "作者:" + item.getAuthor())
                .setText(R.id.tv_category,   item.getDesc())
                .setVisible(R.id.tv_new, item.getNiceDate().contains("前") ? true : false)
                .setImageResource(R.id.iv_save,R.drawable.svg_love_press)
                .addOnClickListener(R.id.iv_save);
    }
}
