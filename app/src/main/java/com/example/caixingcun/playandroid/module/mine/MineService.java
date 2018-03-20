package com.example.caixingcun.playandroid.module.mine;

import com.example.caixingcun.playandroid.net.BaseListData;
import com.example.caixingcun.playandroid.net.ErrorBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by caixingcun on 2018/3/19.
 */

public interface MineService {

    @GET("lg/collect/list/{page}/json")
    Observable<ErrorBean<BaseListData<MyCollectArticleBean>>> getMyCollectList( @Path("page") int page);
}

