package com.example.caixingcun.playandroid.module.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseActivity;
import com.example.caixingcun.playandroid.base.BaseFragment;
import com.example.caixingcun.playandroid.inject.InjectLayout;
import com.example.caixingcun.playandroid.module.firstpage.FirstPageFragment;
import com.example.caixingcun.playandroid.module.guide.GuidePageFragment;
import com.example.caixingcun.playandroid.module.mine.MinePageFragment;
import com.example.caixingcun.playandroid.module.project.ProjectPageFragment;
import com.example.caixingcun.playandroid.module.system.SystemPageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by caixingcun on 2018/3/13.
 */
@InjectLayout(R.layout.activity_home)
public class HomeActivity extends BaseActivity {


    @Bind(R.id.fl)
    FrameLayout mFrameLayout;

    String[] tabs = {"首页", "体系", "导航", "项目", "我的"};
    Class[] fragmentClass = {
            FirstPageFragment.class,
            SystemPageFragment.class,
            GuidePageFragment.class,
            ProjectPageFragment.class,
            MinePageFragment.class
    };
    @Bind(R.id.rb_1)
    RadioButton mRb1;
    @Bind(R.id.rb_2)
    RadioButton mRb2;
    @Bind(R.id.rb_3)
    RadioButton mRb3;
    @Bind(R.id.rb_4)
    RadioButton mRb4;
    @Bind(R.id.rb_5)
    RadioButton mRb5;
    @Bind(R.id.rg)
    RadioGroup mRg;
    private FragmentManager mFm;
    private FirstPageFragment mFirstPageFragment;
    private SystemPageFragment mSystemPageFragment;
    private GuidePageFragment mGuidePageFragment;
    private ProjectPageFragment mProjectPageFragment;
    private MinePageFragment mMinePageFragment;

    List<BaseFragment> mFragments = new ArrayList<>();

    @Override
    protected void initView() {
        mFm = getSupportFragmentManager();
        mFirstPageFragment = new FirstPageFragment();
        mSystemPageFragment = new SystemPageFragment();
        mGuidePageFragment = new GuidePageFragment();
        mProjectPageFragment = new ProjectPageFragment();
        mMinePageFragment = new MinePageFragment();
        mFm.beginTransaction()
                .add(R.id.fl,mFirstPageFragment)
                .add(R.id.fl,mSystemPageFragment)
                .add(R.id.fl,mGuidePageFragment)
                .add(R.id.fl,mProjectPageFragment)
                .add(R.id.fl,mMinePageFragment)
                .hide(mFirstPageFragment)
                .hide(mSystemPageFragment)
                .hide(mGuidePageFragment)
                .hide(mProjectPageFragment)
                .hide(mMinePageFragment)
                .show(mFirstPageFragment)
                .commit();

        mRg.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.rb_1:
                    checkFragment(mFirstPageFragment);
                    break;
                case R.id.rb_2:
                    checkFragment(mSystemPageFragment);
                    break;
                case R.id.rb_3:
                    checkFragment(mGuidePageFragment);
                    break;
                case R.id.rb_4:
                    checkFragment(mProjectPageFragment);
                    break;
                case R.id.rb_5:
                    checkFragment(mMinePageFragment);
                    break;
            }
        });
    }

    private void checkFragment(BaseFragment pageFragment) {
        mFm.beginTransaction()
                .hide(mFirstPageFragment)
                .hide(mSystemPageFragment)
                .hide(mGuidePageFragment)
                .hide(mProjectPageFragment)
                .hide(mMinePageFragment)
                .show(pageFragment)
                .commit();
    }


    @Override
    protected boolean setTransLStatusBar() {
        return true;
    }


}
