package com.example.caixingcun.playandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by caixingcun on 2018/3/14.
 */

public class SharedPreferenceManager {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private Context mContext = null;

    private static SharedPreferenceManager manager = null;

    private SharedPreferenceManager(Context context) {
        mContext = context;
        sp = mContext.getSharedPreferences("sys_setting", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 需要在 App中初始化
     * @param context
     * @return
     */
    public static SharedPreferenceManager getInstance(Context context) {
        if (manager == null) {
            manager = new SharedPreferenceManager(context);
        }
        return manager;
    }

    public static SharedPreferenceManager getInstance() {
        return manager;
    }

    public void put(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    public void put(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void put(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public void put(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }
    public String get(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public int get(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public long get(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }
}
