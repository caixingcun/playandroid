package com.example.caixingcun.playandroid.module.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseActivity;
import com.example.caixingcun.playandroid.inject.InjectLayout;
import com.example.caixingcun.playandroid.module.login.ConstantsLogin;
import com.example.caixingcun.playandroid.net.BaseListData;
import com.example.caixingcun.playandroid.net.DefaultActivityObserver;
import com.example.caixingcun.playandroid.net.ErrorBean;
import com.example.caixingcun.playandroid.net.HttpApi;
import com.example.caixingcun.playandroid.util.SharedPreferenceManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caixingcun on 2018/3/19.
 */
@InjectLayout(R.layout.activity_my_collect)
public class MyCollectActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private int mCurrentPage = 0;
    private List<MyCollectArticleBean> mList;
    private MyCollectArticleAdapter mAdapter;


    @Override
    protected void initView() {
        mTvTitle.setText("我的收藏");
        initRecyclerView();
        getData();
    }

    private void getData() {

        HttpApi.getInstance()
                .create(MineService.class)
                .getMyCollectList(mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultActivityObserver<ErrorBean<BaseListData<MyCollectArticleBean>>>(this) {
                    @Override
                    public void onSuccess(ErrorBean<BaseListData<MyCollectArticleBean>> response) {
                        List<MyCollectArticleBean> datas = response.getData().getDatas();
                        mList.clear();
                        mList.addAll(datas);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new MyCollectArticleAdapter(R.layout.item_article, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

}
