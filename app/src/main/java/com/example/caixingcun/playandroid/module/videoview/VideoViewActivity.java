package com.example.caixingcun.playandroid.module.videoview;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;

import com.dl7.player.media.IjkPlayerView;
import com.dl7.player.widgets.ShareDialog;
import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseActivity;
import com.example.caixingcun.playandroid.inject.InjectLayout;

/**
 * Created by caixingcun on 2018/3/13.
 */
@InjectLayout(R.layout.activity_video_view)
public class VideoViewActivity  extends BaseActivity{
    private IjkPlayerView mPlayerView;
    @Override
    protected void initView() {

        mPlayerView = findViewById(R.id.player_view);
        mPlayerView.init()
                .setVideoPath(Uri.parse("http://covertness.qiniudn" +
                        ".com/android_zaixianyingyinbofangqi_test_baseline.mp4"))
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH)
                .setTitle("邓紫棋")
                .enableDanmaku()
                .start();
    }


    @Override
    protected boolean setTransLStatusBar() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPlayerView.configurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPlayerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mPlayerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
