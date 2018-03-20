package com.example.caixingcun.playandroid.module.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.caixingcun.playandroid.R;
import com.example.caixingcun.playandroid.base.BaseActivity;
import com.example.caixingcun.playandroid.inject.InjectLayout;
import com.just.agentweb.AgentWeb;

import butterknife.Bind;

/**
 * Created by caixingcun on 2018/3/15.
 */
@InjectLayout(R.layout.activity_web)
public class TestWebViewActivity extends BaseActivity {
    @Bind(R.id.ll)
    LinearLayout mLinearLayout;
    private AgentWeb mAgentWeb;

    @Override
    protected void initView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        //do your work

                    }
                })
                .setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        //do your work
                    }
                })
                .createAgentWeb()
                .ready()
                .go("http://www.jd.com");
        //android端吊起js方法
        mAgentWeb.getJsAccessEntrace().quickCallJs("jsMethodName");
        //android注册方法给js调用
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mAgentWeb, this));
    }


    class AndroidInterface {
        public AndroidInterface(AgentWeb agentWeb, Context context) {

        }

        @JavascriptInterface
        public void callAndroid() {
            showToast("js call android");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
}
