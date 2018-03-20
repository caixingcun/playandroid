package com.example.caixingcun.playandroid.module.login;

import com.example.caixingcun.playandroid.net.ErrorBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by caixingcun on 2018/3/14.
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("user/register")
    Observable<ErrorBean<UserInfo>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    @FormUrlEncoded
    @POST("user/login")
    Observable<ErrorBean<UserInfo>> login(@Field("username") String username, @Field("password") String password);

}
