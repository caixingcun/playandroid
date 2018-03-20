package com.example.caixingcun.playandroid.module.home;

import com.example.caixingcun.playandroid.module.home.HomeArticleBean;
import com.example.caixingcun.playandroid.net.BaseListData;
import com.example.caixingcun.playandroid.net.ErrorBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by caixingcun on 2018/3/9.
 */

public interface HomeApiService {


    @GET("article/list/{page}/json")
    Observable<ErrorBean<BaseListData<HomeArticleBean>>> getArticleList(@Path("page") String page);

}

