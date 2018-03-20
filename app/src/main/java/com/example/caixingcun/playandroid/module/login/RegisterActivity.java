package com.example.caixingcun.playandroid.module.login;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
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
import com.example.caixingcun.playandroid.net.DefaultActivityObserver;
import com.example.caixingcun.playandroid.net.ErrorBean;
import com.example.caixingcun.playandroid.net.HttpApi;
import com.example.caixingcun.playandroid.util.SharedPreferenceManager;
import com.example.caixingcun.playandroid.util.SoftInputUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.Bind;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by caixingcun on 2018/3/14.
 */
@InjectLayout(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

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
    @Bind(R.id.tv_to_login)
    TextView mTvToLogin;
    private boolean isFinish;

    @Override
    protected void initView() {
        mTvTitle.setTypeface(getTf());


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Observable.combineLatest(RxTextView.textChanges(mEtAccount), RxTextView.textChanges(mEtPassword), (charSequence, charSequence2) ->
                !TextUtils.isEmpty(charSequence) && !TextUtils.isEmpty(charSequence2)
        ).subscribe(b -> mBtnLogin.setEnabled(b));

        RxView.clicks(mTvToLogin).subscribe(v -> {
            ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, mTvTitle, "login");
            startActivity(new Intent(mActivity, LoginActivity.class), option.toBundle());
            isFinish = true;
        });
        RxView.clicks(mBtnLogin).subscribe((Object v) -> {

            HttpApi.getInstance()
                    .create(LoginService.class)
                    .register(mEtAccount.getText().toString().trim(), mEtPassword.getText().toString().trim(), mEtPassword.getText().toString().trim())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultActivityObserver<ErrorBean<UserInfo>>(this) {
                        @Override
                        public void onSuccess(ErrorBean<UserInfo> response) {
                            UserInfo data = response.getData();
                            SharedPreferenceManager.getInstance().put(ConstantsLogin.TAG_USER_NAME, data.getUsername());
                            SharedPreferenceManager.getInstance().put(ConstantsLogin.TAG_PASS_WORD, data.getPassword());

                            ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, mTvTitle, "login");
                            startActivity(new Intent(mActivity, LoginActivity.class), option.toBundle());
                            isFinish = true;
                        }
                    });

        });

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinish) {
            finish();
        }
    }

    public Typeface getTf() {
        AssetManager am = getAssets();
        return Typeface.createFromAsset(am, "font/wendy.ttf");
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
