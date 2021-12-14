package com.u21.a0903_onlinemusic;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
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
        webView.loadUrl("http://bakaec.oicp.io");
        client=new MyClient();
        webView.setWebViewClient(client);
        WebSettings settings= webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");
        webView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) { // 表示按返回键时的操作
                        webView.goBack(); // 后退

                        return true;
                    }
                }
                return false;
            }
        });

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
