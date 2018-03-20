package com.example.caixingcun.playandroid.module.detail;

import android.view.View;
import android.widget.TextView;

import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseFragment;
import com.example.caixingcun.playandroid.inject.InjectLayout;
import com.example.caixingcun.playandroid.module.home.HomeApiService;
import com.example.caixingcun.playandroid.module.home.HomeArticleBean;
import com.example.caixingcun.playandroid.net.BaseListData;
import com.example.caixingcun.playandroid.net.DefaultFragmentObserver;
import com.example.caixingcun.playandroid.net.ErrorBean;
import com.example.caixingcun.playandroid.net.HttpApi;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caixingcun on 2018/3/9.
 */
@InjectLayout(R.layout.activity_test)
public class Fragment1 extends BaseFragment {

    private TextView tv;

    @Override
    protected void initView(View view) {
        tv = view.findViewById(R.id.tv);
        tv.setText("fragment1");
        view.findViewById(R.id.btn).setOnClickListener(v->{
            apiRequest();
        });
    }



    private void apiRequest() {
        HttpApi.getInstance()
                .create(HomeApiService.class)
                .getArticleList("1")
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultFragmentObserver<ErrorBean<BaseListData<HomeArticleBean>>>(this) {
                    @Override
                    public void onSuccess(ErrorBean<BaseListData<HomeArticleBean>> articleBean) {
                        tv.setText(articleBean.getData().getDatas().get(0).getTitle());
                    }
                });

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl, new Fragment2())
                .commit()
        ;
    }
}
