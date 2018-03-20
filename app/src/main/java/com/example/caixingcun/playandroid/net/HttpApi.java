package com.example.caixingcun.playandroid.net;

import com.example.caixingcun.playandroid.App;
import com.example.caixingcun.playandroid.util.LogUtils;
import com.example.caixingcun.playandroid.util.NetworkUtils;
import com.example.caixingcun.playandroid.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by caixingcun on 2018/3/9.
 *  retrofit 网络请求api
 */

public class HttpApi {
    private static HttpApi instance;
    private static Retrofit  retrofit;
    private HttpApi() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
            try {
               String text =  URLDecoder.decode(message, "utf-8");
                LogUtils.e("OKHttp-----", text);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile = new File(Utils.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new AddCookiesInterceptor(App.getAppContext()))
                .addInterceptor(new ReceivedCookiesInterceptor(App.getAppContext()))
      //          .addInterceptor(new HttpHeaderInterceptor())
      //          .addNetworkInterceptor(new HttpCacheInterceptor())
//                 .sslSocketFactory(SslContextFactory.getSSLSocketFactoryForTwoWay())   https认证 如果要使用https且为自定义证书 可以去掉这两行注释，并自行配制证书。
                // .hostnameVerifier(new SafeHostnameVerifier())
                .cache(cache)
                .build();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.HOST)
                .build();
    }

    public static Retrofit getInstance() {
        if (null == instance) {
            instance = new HttpApi();
        }
        return retrofit;
    }


    //  添加请求头的拦截器
    private class HttpHeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //  配置请求头
            String accessToken = "token";
            String tokenType = "tokenType";
            Request request = chain.request().newBuilder()
                    .header("app_key","appId")
                    .header("Authorization", tokenType + " " + accessToken)
                    .header("Content-Type", "application/json")
                    .addHeader("Connection", "close")
                    .build();
            return chain.proceed(request);
        }
    }

    class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

    /**
     * 验证请求主机地址ip是否为指定ip
     */
    private class SafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            if (Constants.IP.equals(hostname)) {//校验hostname是否正确，如果正确则建立连接
                return true;
            }
            return false;
        }
    }

  private  TrustManager tm = new X509TrustManager() {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            //do nothing 接收任意客户端证书
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            //接收任意服务端证书
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        //    return new X509Certificate[0];
        }
    };
}
