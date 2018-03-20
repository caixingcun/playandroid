package com.example.caixingcun.playandroid.module.firstpage;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.module.firstpage.AritcleBean;

import java.util.List;

/**
 * Created by caixingcun on 2018/3/14.
 */

public class SimpleRcyclerViewAdapter extends BaseQuickAdapter<AritcleBean, BaseViewHolder> {
    public SimpleRcyclerViewAdapter(int layoutResId, @Nullable List<AritcleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AritcleBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_author, "作者:" + item.getAuthor())
                .setText(R.id.tv_category, "分类:" + item.getSuperChapterName() + "/" + item.getChapterName() + " " + item.getNiceDate())
                .setVisible(R.id.tv_new, item.getNiceDate().contains("前") ? true : false)
                .setImageResource(R.id.iv_save,item.isCollect()?R.drawable.svg_love_press:R.drawable.svg_love)
                .addOnClickListener(R.id.iv_save);
    }
}
