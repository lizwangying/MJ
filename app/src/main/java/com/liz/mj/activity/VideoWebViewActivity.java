package com.liz.mj.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liz.mj.BaseActivity;
import com.liz.mj.R;
import com.liz.mj.bean.MJVideos;

import butterknife.Bind;

public class VideoWebViewActivity extends BaseActivity {
    @Bind(R.id.webview_mj_personal)
    WebView webView;
    private MJVideos videos;

    @Override
    protected void onBeforeSetContentLayout() {
        setLayoutId(R.layout.activity_mj_personal_info);
    }

    @Override
    public void initView() {

        Intent intent = this.getIntent();
        videos = (MJVideos) intent.getSerializableExtra("MJVideos");


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(false);
        //出现横向滚动条，把所有内容放到webview组件等宽的一列中
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        webView.setWebViewClient(new WebViewClient(){
            //点击链接覆盖而不是打开新的窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //监听回退键
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
                        webView.goBack();
                        return true;//已处理
                    }
                }
                return false;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //更新窗口进度，Activity的进度范围在0到10000之间，所以乘以100
                VideoWebViewActivity.this.setProgress(newProgress*100);
            }
        });

        webView.loadUrl(videos.getVideoAddress());
        webView.requestFocus();
    }
}
