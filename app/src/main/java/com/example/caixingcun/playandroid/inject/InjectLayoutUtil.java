package com.example.caixingcun.playandroid.inject;

import android.util.Log;

/**
 * Created by caixingcun on 2018/3/12.
 */

public class InjectLayoutUtil {
    /**
     * 获取容器的布局id
     *
     * @param container the container of InjectLayout
     * @return the layout id
     */
    public static int getInjectLayoutId(Object container) {
        InjectLayout injectLayout = container.getClass().getAnnotation(InjectLayout.class);
        if (injectLayout == null) {
            Log.d("InjectLayoutUtil", "Can not find annotation 'InjectLayout' on " + container.getClass().getName() + ".");
            return 0;
        }
        return injectLayout.value();
    }
}
