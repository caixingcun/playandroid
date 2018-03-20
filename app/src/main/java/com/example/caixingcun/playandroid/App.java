package com.example.caixingcun.playandroid;

import android.app.Application;
import android.content.Context;

import com.example.caixingcun.playandroid.imageloader.GlideImageLoader;
import com.example.caixingcun.playandroid.util.SharedPreferenceManager;
import com.example.caixingcun.playandroid.util.Utils;
import com.example.daomodule.greendao.DaoManager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

/**
 * Created by caixingcun on 2018/3/9.
 */

public class App extends Application {

    private static App app;
    public static Context getAppContext() {
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        app=this;
    //    DaoManager.init(app);
        initImagePicker();
        SharedPreferenceManager.getInstance(app);
    }

    /**
     * 初始化图片选择器
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        //程序在内存清理时


    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }
}
