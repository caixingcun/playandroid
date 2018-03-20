package com.example.caixingcun.playandroid.module.login;

import android.support.v4.app.ActivityCompat;

import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseActivity;
import com.example.caixingcun.playandroid.inject.InjectLayout;
import com.example.caixingcun.playandroid.module.home.HomeActivity;
import com.example.caixingcun.playandroid.net.DefaultActivityObserver;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by caixingcun on 2018/3/13.
 */
@InjectLayout(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
    @Override
    protected void initView() {
        Observable.just(1).delay(1500, TimeUnit.MILLISECONDS)

                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        launch(LoginActivity.class);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected boolean setTransLStatusBar() {
        return true;
    }
}
