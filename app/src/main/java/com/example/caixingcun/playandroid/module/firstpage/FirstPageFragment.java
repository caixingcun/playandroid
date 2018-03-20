package com.example.caixingcun.playandroid.module.firstpage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseActivity;
import com.example.caixingcun.playandroid.base.BaseFragment;
import com.example.caixingcun.playandroid.inject.InjectLayout;
import com.example.caixingcun.playandroid.module.search.ArticleSearchActivity;
import com.example.caixingcun.playandroid.module.webview.WebViewDeatilActivity;
import com.example.caixingcun.playandroid.net.BaseListData;
import com.example.caixingcun.playandroid.net.DefaultFragmentObserver;
import com.example.caixingcun.playandroid.net.ErrorBean;
import com.example.caixingcun.playandroid.net.HttpApi;
import com.example.caixingcun.playandroid.util.LogUtils;
import com.example.caixingcun.playandroid.util.ToastUtils;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by caixingcun on 2018/3/14.
 */
@InjectLayout(R.layout.fragment_first_page)
public class FirstPageFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private SimpleRcyclerViewAdapter mAdapter;
    private List<AritcleBean> mList;
    private int mCurrentPage = 0;
    private Banner mBanner;


    @Override
    protected void initView(View view) {
        initRecyclerView();
        initRefreshLayout();
        getData();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setRefreshHeader(new WaterDropHeader(getContext()));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));

        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            getFirstPageList();
        });
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 0;
            getFirstPageList();
        });
    }

    private void getData() {
        getBannerData();
        getFirstPageList();
    }

    private void getFirstPageList() {
        HttpApi.getInstance()
                .create(FirstPageService.class)
                .getArticleList(mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultFragmentObserver<ErrorBean<BaseListData<AritcleBean>>>(this) {
                    @Override
                    public void onSuccess(ErrorBean<BaseListData<AritcleBean>> response) {
                        if (mCurrentPage == 0) {
                            mList.clear();
                        }
                        mList.addAll(response.getData().getDatas());
                        mAdapter.notifyDataSetChanged();

                        if (mCurrentPage == 0) {
                            mRefreshLayout.finishRefresh();
                        } else {
                            mRefreshLayout.finishLoadMore();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mCurrentPage == 0) {
                            mRefreshLayout.finishRefresh(false);
                        } else {
                            mRefreshLayout.finishLoadMore(false);
                        }
                    }
                });
    }

    private void getBannerData() {
        HttpApi.getInstance()
                .create(FirstPageService.class)
                .getBannerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultFragmentObserver<ErrorBean<List<BannerBean>>>(this) {
                    @Override
                    public void onSuccess(ErrorBean<List<BannerBean>> response) {

                        initBanner(response.getData());
                    }
                });
    }

    private void initBanner(List<BannerBean> data) {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        Stream.of(data).forEach(banner -> {
            images.add(banner.getImagePath());
            titles.add(banner.getTitle());
        });
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context)
                                .load(path)
                                .into(imageView);
                    }
                }).setImages(images)
                .setBannerTitles(titles)
                .isAutoPlay(true)
                .setDelayTime(1500)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();
        mBanner.setOnBannerListener(position -> {
            Intent intent = new Intent(getActivity(), WebViewDeatilActivity.class);
            intent.putExtra("url", data.get(position).getUrl());
            startActivity(intent);
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        mList = new ArrayList<>();
        mAdapter = new SimpleRcyclerViewAdapter(R.layout.item_article, mList);

        mRecyclerView.setAdapter(mAdapter);
        //  View headView = View.inflate(getContext(), R.layout.item_banner, null);
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.item_banner, mRecyclerView, false);
        mBanner = headView.findViewById(R.id.banner);
        mAdapter.addHeaderView(headView);
        View searchView = LayoutInflater.from(getContext()).inflate(R.layout.item_head_title, mRecyclerView, false);
        mAdapter.addHeaderView(searchView);
        searchView.setOnClickListener(v -> startActivity(new Intent(getActivity(), ArticleSearchActivity.class)));
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), WebViewDeatilActivity.class);
            intent.putExtra("url", mList.get(position).getLink());
            startActivity(intent);
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (mList.get(position).isCollect()) {
                unArticleCollect(mList.get(position).getId(), position);
            } else {
                LogUtils.d(TAG, "before collect");
                LogUtils.d(TAG, mFragment.getContext().getSharedPreferences("cookie", MODE_PRIVATE).getString("cookie", "cookie is empty"));
                collectArticle(mList.get(position).getId(), position);
            }
        });
    }

    private void unArticleCollect(int id, int position) {
        HttpApi.getInstance()
                .create(FirstPageService.class)
                .unCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultFragmentObserver<ErrorBean>(this) {
                    @Override
                    public void onSuccess(ErrorBean response) {
                        mList.get(position).setCollect(mList.get(position).isCollect());
                        getFirstPageList();
                        ((BaseActivity) getActivity()).showToast("取消收藏");
                    }
                });
    }

    private void collectArticle(int id, int position) {
        HttpApi.getInstance()
                .create(FirstPageService.class)
                .collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultFragmentObserver<ErrorBean>(this) {
                    @Override
                    public void onSuccess(ErrorBean response) {
                        ToastUtils.show("收藏成功");
                        getFirstPageList();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

}
