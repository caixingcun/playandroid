package com.example.caixingcun.playandroid.module.guide;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseFragment;
import com.example.caixingcun.playandroid.inject.InjectLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by caixingcun on 2018/3/14.
 */
@InjectLayout(R.layout.fragment_guide)
public class GuidePageFragment extends BaseFragment {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void initView(View view) {
    mTvTitle.setText("引导");
    }

}
