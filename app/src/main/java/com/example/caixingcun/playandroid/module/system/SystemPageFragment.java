package com.example.caixingcun.playandroid.module.system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseActivity;
import com.example.caixingcun.playandroid.base.BaseFragment;
import com.example.caixingcun.playandroid.inject.InjectLayout;
import com.example.caixingcun.playandroid.net.DefaultFragmentObserver;
import com.example.caixingcun.playandroid.net.ErrorBean;
import com.example.caixingcun.playandroid.net.HttpApi;
import com.example.caixingcun.playandroid.util.ACache;
import com.example.caixingcun.playandroid.util.DisplayUtil;
import com.example.caixingcun.playandroid.util.ToastUtils;
import com.example.caixingcun.playandroid.util.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

/**
 * Created by caixingcun on 2018/3/14.
 */
@InjectLayout(R.layout.fragment_system)
public class SystemPageFragment extends BaseFragment {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Bind(R.id.status_space)
    View mStatusSpace;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        setIconsVisible(menu, true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                getData();
                break;
        }
        return true;
    }

    /**
     * 解决不显示menu icon的问题
     */
    public void setIconsVisible(Menu menu, boolean flag) {
        //判断menu是否为空
        if (menu != null) {
            try {
                //如果不为空,就反射拿到menu的setOptionalIconsVisible方法
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",
                        Boolean.TYPE);
                //暴力访问该方法
                method.setAccessible(true);
                //调用该方法显示icon
                method.invoke(menu, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initView(View view) {
        mTvTitle.setText("体系");
        int statusHeight = DisplayUtil.getStatusHeight(getActivity());
        ViewGroup.LayoutParams layoutParams = mStatusSpace.getLayoutParams();
        layoutParams.height = statusHeight;

        mStatusSpace.setLayoutParams(layoutParams);
        mToolbar.setTitle("");
        ((BaseActivity) getActivity()).setSupportActionBar(mToolbar);


        //getData();
    }

    private void getData() {
        String treeCache = getTreeCache();
        if (null == treeCache) {
           requestTreeCache();
        } else {
            ErrorBean<List<CategoryBean<CategoryBean<Object>>>> response = new Gson().fromJson(treeCache, new TypeToken<ErrorBean<List<CategoryBean<CategoryBean<Object>>>>>() {
            }.getType());
            handleTreeData(response);
        }

    }

    private void requestTreeCache() {
        HttpApi.getInstance()
                .create(SystemService.class)
                .getTreeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultFragmentObserver<ErrorBean<List<CategoryBean<CategoryBean<Object>>>>>(this) {
                    @Override
                    public void onSuccess(ErrorBean<List<CategoryBean<CategoryBean<Object>>>> response) {
                        saveCache(response);
                        handleTreeData(response);
                    }
                });
    }

    private void handleTreeData(ErrorBean<List<CategoryBean<CategoryBean<Object>>>> response) {

    }

    private String getTreeCache() {
        return ACache.get(mFragment.getContext()).getAsString("tree_data");
    }


    private void saveCache(ErrorBean<List<CategoryBean<CategoryBean<Object>>>> response) {
        Gson gson = new Gson();
        String s = gson.toJson(response);
        ACache.get(mFragment.getContext()).put("tree_data", s);
    }


}
