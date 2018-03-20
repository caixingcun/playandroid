package com.example.caixingcun.playandroid.module.detail;

import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseActivity;
import com.example.caixingcun.playandroid.inject.InjectLayout;

/**
 * Created by caixingcun on 2018/3/9.
 */
@InjectLayout(R.layout.activity_second)
public class SecondActivity extends BaseActivity {



    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction().add(R.id.fl, new Fragment1())
        .commit()
        ;
    }
}
