package com.example.caixingcun.playandroid.module.home;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.bumptech.glide.Glide;
import com.dl7.player.media.IjkPlayerView;
import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseActivity;
import com.example.caixingcun.playandroid.imageloader.GlideImageLoader;
import com.example.caixingcun.playandroid.inject.InjectLayout;
import com.example.caixingcun.playandroid.module.videoview.VideoViewActivity;
import com.example.caixingcun.playandroid.net.BaseListData;
import com.example.caixingcun.playandroid.net.DefaultActivityObserver;
import com.example.caixingcun.playandroid.net.ErrorBean;
import com.example.caixingcun.playandroid.net.HttpApi;
import com.example.daomodule.EntityManager;
import com.example.daomodule.entity.User;
import com.example.daomodule.greendao.gen.UserDao;
import com.jakewharton.rxbinding2.view.RxView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectLayout(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private ImageView mIv;


    @Override
    protected void initToolbar() {
        super.initToolbar();
        if (mTvTitle != null) {
            mTvTitle.setText("主界面");
        }
    }

    @Override
    protected void initView() {
        RxView.clicks(findViewById(R.id.btn)).subscribe(v -> {
            User user = EntityManager.getEntityManager().getUserDao().queryBuilder().where(UserDao.Properties.Page.eq("1")).unique();
            if (user == null) {
                apiRequest();
            } else {
                Toast.makeText(this, "从缓存取", Toast.LENGTH_SHORT).show();
                ((TextView) findViewById(R.id.tv)).setText(user.getContent());
            }
        });
        RxView.clicks(findViewById(R.id.btn_1)).subscribe(v -> {
            new RxPermissions(this)
                    .request(Manifest.permission.CAMERA)
                    .subscribe(granted -> {
                        if (granted) {
                            Toast.makeText(this, "请求拍照权限成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "请求拍照权限失败", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        mIv = findViewById(R.id.iv);
        RxView.clicks(mIv).subscribe(v -> {
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, 101);
        });

        RxView.clicks(findViewById(R.id.btn_video_view)).subscribe(v->{
            startActivity(new Intent(this,VideoViewActivity.class));
        });
    }



    private void apiRequest() {

        HttpApi.getInstance()
                .create(HomeApiService.class)
                .getArticleList("1")
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultActivityObserver<ErrorBean<BaseListData<HomeArticleBean>>>(this) {
                    @Override
                    public void onSuccess(ErrorBean<BaseListData<HomeArticleBean>> articleBean) {
                        Toast.makeText(MainActivity.this, "从服务器获取", Toast.LENGTH_SHORT).show();
                        EntityManager.getEntityManager().getUserDao().insert(new User(null, "1", articleBean.getData().getDatas().get(0).getTitle()));
                        ((TextView) findViewById(R.id.tv)).setText(articleBean.getData().getDatas().get(0).getTitle());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 101) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images.size() > 0) {
                    Glide.with(this)
                            .load(images.get(0).path)
                            .into(mIv);
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
