package com.u21.a0903_onlinemusic;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebActivity extends ActivityGroup {
    private WebView webView;
    private MyClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myweb);
        webView=findViewById(R.id.web);
        webView.loadUrl("http://bakaec.cloud");
        client=new MyClient();
        webView.setWebViewClient(client);
        WebSettings settings= webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");

    }

    class MyClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url){
            //view 设置该接口的webView
            //url 发生改变后的url地址
            //webview发生变化时重新加载地址
            view.loadUrl(url);
            return true;
        }
    }
}
