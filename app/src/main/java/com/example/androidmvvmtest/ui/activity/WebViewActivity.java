package com.example.androidmvvmtest.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.base.BaseActivity;
import com.example.androidmvvmtest.databinding.ActivityWebViewBinding;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/14 16:11
 * @Description: TODO
 */
public class WebViewActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWebViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);

        binding.webView.setWebViewClient(client);
        if (getIntent().getIntExtra("kind", 1) == 0) {
            binding.webView.loadData(getIntent().getStringExtra("url"), "text/html; charset=utf-8", "UTF-8");
        } else if (getIntent().getIntExtra("kind", 1) == 1) {
            binding.webView.loadUrl(getIntent().getStringExtra("url"));
            binding.webView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("TAGG","11");
                    openBrowser(getIntent().getStringExtra("url"));
                }
            });
        }
    }


    private final WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView webview,
                                              com.tencent.smtt.export.external.interfaces.HttpAuthHandler httpAuthHandlerhost, String host,
                                              String realm) {
            boolean flag = httpAuthHandlerhost.useHttpAuthUsernamePassword();
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            System.out.println("***********onReceivedError ************");
            super.onReceivedError(webView, i, s, s1);
        }

        @Override
        public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
            System.out.println("***********onReceivedHttpError ************");
            super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        }
    };

    /**
     * 跳转浏览器
     * @param url 网址
     */
    private void openBrowser(String url) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction("android.intent.action.VIEW");
        startActivity(intent);
    }
}
