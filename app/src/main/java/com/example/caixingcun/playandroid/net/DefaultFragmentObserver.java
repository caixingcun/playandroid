package com.example.caixingcun.playandroid.net;

import android.text.TextUtils;
import android.widget.Toast;

import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseActivity;
import com.example.caixingcun.playandroid.base.BaseFragment;
import com.example.caixingcun.playandroid.util.LogUtils;
import com.example.caixingcun.playandroid.util.ToastUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.adapter.rxjava2.HttpException;




/**
 * Created by zhpan on 2017/4/18.
 */

public abstract class DefaultFragmentObserver<T extends ErrorBean> implements Observer<T> {
    private BaseFragment fragment;
    //  Activity 是否在执行onStop()时取消订阅
    private boolean isAddInStop = false;
//    private CommonDialogUtils dialogUtils;
    public DefaultFragmentObserver(BaseFragment fragment) {
        this.fragment = fragment;
//        dialogUtils=new CommonDialogUtils();
//        dialogUtils.showProgress(activity);
    }

    public DefaultFragmentObserver(BaseFragment fragment, boolean isShowLoading) {
        this.fragment = fragment;
//        dialogUtils=new CommonDialogUtils();
//        if (isShowLoading) {
//            dialogUtils.showProgress(activity,"Loading...");
//        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        fragment.compositeDisposable.add(d);
    }

    @Override
    public void onNext(T response) {
        dismissProgress();
        if (response.errorCode == 0) {
            onSuccess(response);
        } else {
            onFail(response);
        }
    }

    private void dismissProgress(){
//        if(dialogUtils!=null){
//            dialogUtils.dismissProgress();
//        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e("Retrofit", e.getMessage());
        dismissProgress();
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为200
     *
     * @param response 服务器返回的数据
     */
    public void onFail(T response) {
        String message = response.getErrorMsg();
        if (TextUtils.isEmpty(message)) {
            ToastUtils.show(R.string.response_return_error);
        } else {
            ToastUtils.show(message);
        }
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.show(R.string.connect_error, Toast.LENGTH_SHORT);
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.show(R.string.connect_timeout, Toast.LENGTH_SHORT);
                break;

            case BAD_NETWORK:
                ToastUtils.show(R.string.bad_network, Toast.LENGTH_SHORT);
                break;

            case PARSE_ERROR:
                ToastUtils.show(R.string.parse_error, Toast.LENGTH_SHORT);
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtils.show(R.string.unknown_error, Toast.LENGTH_SHORT);
                break;
        }
    }



}
