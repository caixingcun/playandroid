package com.example.caixingcun.playandroid.module.system;


import com.example.caixingcun.playandroid.net.ErrorBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by caixingcun on 2018/3/19.
 */

public interface SystemService {
    @GET("tree/json")
    Observable<ErrorBean<List<CategoryBean<CategoryBean<Object>>>>> getTreeData();
}

