package com.example.caixingcun.playandroid.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.caixingcun.playandroid.inject.InjectLayoutUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by caixingcun on 2018/3/9.
 */

public abstract class BaseFragment extends Fragment {
    public CompositeDisposable compositeDisposable;
    public Fragment mFragment;
    public static String TAG;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        mFragment = this;
        TAG = getClass().getCanonicalName().toString();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          View  mRootView = inflater.inflate(InjectLayoutUtil.getInjectLayoutId(this), null);
            ButterKnife.bind(this, mRootView);
            initView(mRootView);
        return mRootView;
    }


    protected abstract void initView(View view);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        ButterKnife.unbind(this);

    }
}
