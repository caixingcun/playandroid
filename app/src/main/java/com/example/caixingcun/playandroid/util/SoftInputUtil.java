/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.example.caixingcun.playandroid.util;

import android.content.Context;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by jerry.guan on 3/8/2016.
 * 软键盘操作工具类
 */
public class SoftInputUtil {

    /**
     * 软键盘显示
     *
     * @param context
     */
    public static void show(Context context) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(((AppCompatActivity)context).getCurrentFocus().getWindowToken(),
                InputMethodManager.RESULT_SHOWN);
    }

    /**
     * 软键盘隐藏
     *
     * @param context
     */
    public static void hidden(Context context) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view=((AppCompatActivity)context).getCurrentFocus();
        if(view!=null){
            im.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    /**
     * view获取焦点软键盘打开
     *
     * @param context
     */
    public static void showInputAsView(Context context, View view) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(view, 0);
    }

    public static boolean isShow(Context context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();//isOpen若返回true，则表示输入法打开
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    public static void hideKeyboard(IBinder token, Context context) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
