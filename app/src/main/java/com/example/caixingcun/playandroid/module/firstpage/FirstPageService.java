package com.example.caixingcun.playandroid.module.firstpage;

import com.example.caixingcun.playandroid.net.BaseListData;
import com.example.caixingcun.playandroid.net.ErrorBean;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by caixingcun on 2018/3/16.
 */

public interface FirstPageService {
    /**
     * 获取广告栏信息
     * @return
     */
    @GET("banner/json")
    Observable<ErrorBean<List<BannerBean>>> getBannerList();

    /**
     * 获取文章列表
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<ErrorBean<BaseListData<AritcleBean>>> getArticleList(@Path("page") int page);

    /**
     * 取消收藏
     * @param id
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<ErrorBean> unCollectArticle(@Path("id") int id);

    /**
     * 收藏站内文章
     * @param id
     * @return
     */
    @POST("lg/collect/{id}/json")
    Observable<ErrorBean> collectArticle(@Path("id") int id);
}

