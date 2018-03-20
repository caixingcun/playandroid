package com.example.caixingcun.playandroid.module.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseFragment;
import com.example.caixingcun.playandroid.inject.InjectLayout;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;

/**
 * Created by caixingcun on 2018/3/14.
 */
@InjectLayout(R.layout.fragment_mine)
public class MinePageFragment extends BaseFragment {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_my_save)
    TextView mTvMySave;
    @Override
    protected void initView(View view) {
        mTvTitle.setText("我的");
        RxView.clicks(mTvMySave)
                .throttleFirst(300, TimeUnit.MILLISECONDS)
                .subscribe(v->{
                    startActivity(new Intent(getActivity(),MyCollectActivity.class));
                });
    }

}
