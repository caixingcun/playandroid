package com.example.caixingcun.playandroid.module.login;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseActivity;
import com.example.caixingcun.playandroid.inject.InjectLayout;
import com.example.caixingcun.playandroid.module.home.HomeActivity;
import com.example.caixingcun.playandroid.net.DefaultActivityObserver;
import com.example.caixingcun.playandroid.net.ErrorBean;
import com.example.caixingcun.playandroid.net.HttpApi;
import com.example.caixingcun.playandroid.util.LogUtils;
import com.example.caixingcun.playandroid.util.SharedPreferenceManager;
import com.example.caixingcun.playandroid.util.SoftInputUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by caixingcun on 2018/3/13.
 */
@InjectLayout(R.layout.activity_login)
public class LoginActivity extends BaseActivity {


    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.et_account)
    EditText mEtAccount;
    @Bind(R.id.et_password)
    TextInputEditText mEtPassword;
    @Bind(R.id.til_password)
    TextInputLayout mTilPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.tv_register)
    TextView mTvRegister;
    @Bind(R.id.tv_forget_password)
    TextView mTvForgetPassword;
    private boolean isFinish;


    @Override
    protected void initView() {
        mTvTitle.setTypeface(getTf());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Observable.combineLatest(RxTextView.textChanges(mEtAccount).skipInitialValue(), RxTextView.textChanges(mEtPassword).skipInitialValue(),
                (charSequence1, charSequence) -> !TextUtils.isEmpty(charSequence1) && !TextUtils.isEmpty(charSequence)).subscribe(
                b -> mBtnLogin.setEnabled(b));

        RxView.clicks(mTvRegister).subscribe(v -> {
            ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(this, findViewById(R.id.tv_title), "login");
            startActivity(new Intent(this, RegisterActivity.class), option.toBundle());
            isFinish = true;
        });
        RxView.clicks(mBtnLogin).subscribe(v -> {
            login();
        });

        getData();
    }

    private void login() {
        HttpApi.getInstance()
                .create(LoginService.class)
                .login(mEtAccount.getText().toString().trim(), mEtPassword.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultActivityObserver<ErrorBean<UserInfo>>(this) {
                    @Override
                    public void onSuccess(ErrorBean<UserInfo> info) {
                        LogUtils.d(TAG, "login success");
                        saveUserInfo(info.getData());
                        launch(HomeActivity.class);
                        finish();
                    }
                });
    }

    private void saveUserInfo(UserInfo info) {
        SharedPreferenceManager.getInstance().put(ConstantsLogin.TAG_USER_NAME,info.getUsername());
        SharedPreferenceManager.getInstance().put(ConstantsLogin.TAG_PASS_WORD,info.getPassword());
    }

    private void getData() {
        String userName = SharedPreferenceManager.getInstance().get(ConstantsLogin.TAG_USER_NAME, "");
        String password = SharedPreferenceManager.getInstance().get(ConstantsLogin.TAG_PASS_WORD, "");
        mEtAccount.setText(userName);
        mEtPassword.setText(password);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinish == true) {
            finish();
        }
    }

    public Typeface getTf() {
        AssetManager am = getAssets();
        return Typeface.createFromAsset(am, "font/BrushScriptStd.ttf");
    }

    /**
     * 点击屏幕隐藏软键盘
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (SoftInputUtil.isShouldHideKeyboard(v, ev)) {
                SoftInputUtil.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
