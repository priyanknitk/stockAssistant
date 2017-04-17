package com.assistant.priyank.android.assistant.Utilities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.assistant.priyank.android.assistant.R;

public class StockNewsActivity extends AppCompatActivity {

    private WebView mWebView;
    private static final String BASE_URL = "https://in.finance.yahoo.com/quote/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_news);
        mWebView = (WebView) findViewById(R.id.stock_web_view);
        Intent intent = getIntent();
        String ticker = intent.getStringExtra("TICKER");
        mWebView.loadUrl(BASE_URL + ticker);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }
}
