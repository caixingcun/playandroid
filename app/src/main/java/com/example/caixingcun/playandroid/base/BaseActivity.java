package com.example.caixingcun.playandroid.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.inject.InjectLayoutUtil;
import com.example.caixingcun.playandroid.util.ActivityCollector;
import com.example.caixingcun.playandroid.util.LogUtils;
import com.example.caixingcun.playandroid.util.SystemBarTintManager;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by caixingcun on 2018/3/9.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public CompositeDisposable mCompositeDisposable;
    public static String TAG;
    private SystemBarTintManager tintManager;
    protected Toolbar mToolbar;
    protected TextView mTvTitle;
    public Activity mActivity;
    /**
     * @param cls 启动一个activity的公共方法
     */
    public void launch(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getCanonicalName().toString();
        initStatusBar();

        setContentView( InjectLayoutUtil.getInjectLayoutId(this));

        ButterKnife.bind(this);
        mCompositeDisposable = new CompositeDisposable();
        mActivity = this;
        ActivityCollector.addActivity(this);
        initToolbar();
        initView();
    }

    protected void initToolbar() {
        View view = findViewById(R.id.toolbar);
        if (view != null) {
            mToolbar = (Toolbar) view;
            mToolbar.setTitle("");
            mToolbar.setSubtitle("");
            mToolbar.setLogo(null);
            mTvTitle = findViewById(R.id.tv_title);
            //去除内间距
            mToolbar.setContentInsetsAbsolute(0, 0);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            setSupportActionBar(mToolbar);
        }
    }

    /**
     * status bar
     */
    private void initStatusBar() {
        //设置状态栏为 主题色
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (setTransLStatusBar()) {
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                window.setStatusBarColor(typedValue.data);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //沉浸式状态栏
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(typedValue.resourceId);
    }

    /**
     * 设置透明状态栏  true 为透明状态栏  需要重写
     * false为正常沉浸式与主题色一致
     *
     * @return
     */
    protected boolean setTransLStatusBar() {
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        LogUtils.d(getClass().getCanonicalName().toString() + "-" + "onDestroy");
    }


    protected abstract void initView();

    /**
     * Toast工具类
     */
    public Toast toast;

    public void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(mActivity, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    /**
     * 实现连续点击两次才退出应用程序
     */
    private long lastClickTime;

    public void exit() {
        if ((System.currentTimeMillis() - lastClickTime) > 2000) {
            showToast("再按一次退出" + mActivity.getResources().getString(R.string.app_name));
            lastClickTime = System.currentTimeMillis();
        } else {
            ActivityCollector.removeAll();
            System.exit(0);
        }
    }

    /*************************** 判断应用是否安装 **********************************/
    /**
     * 微信包名
     */
    public final String TENCENT_WECHAT_PACKAGENAME = "com.tencent.mm";
    /**
     * 支付宝包名
     */
    public final String ALIPAY_PACKAGENAME = "com.eg.android.AlipayGphone";

    /**
     * 判断某应用是否安装
     *
     * @param mContext
     * @param packageName
     * @return
     */
    public boolean isInstallPackage(Context mContext, String packageName) {
        PackageManager pManager = mContext.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pManager.getPackageInfo(packageName,
                    PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (packageInfo == null) ? false : true;
    }

}
