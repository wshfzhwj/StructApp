package com.sf.struct.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sf.back.R;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    public static final String TAG = "WebActivity";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_web);
        initView();
        initData();
    }

    /**
     * 初始化视图资源
     */
    private void initView() {
        mWebView = (WebView) findViewById(R.id.wb_vue);
    }

    @JavascriptInterface
    public void hello(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("SetJavaScriptEnabled")
    public void testJS() {
        mWebView.loadUrl("javascript:test()");
    }
    /**
     * 加载初始数据
     */
    @SuppressLint("SetJavaScriptEnabled")
    protected void initData() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAllowContentAccess(true);
        //  settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //  mWebView.addJavascriptInterface(this, "justTest");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            settings.setAllowFileAccessFromFileURLs(true);
//            settings.setAllowUniversalAccessFromFileURLs(true);
//        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("noSepPath", "shouldOverrideUrlLoading==" + url);
                Uri uri = Uri.parse(url);
                String scheme = uri.getScheme();
                Log.e("noSepPath", "uri.getScheme() ==" + scheme);
                if (!"http".equals(scheme) || !"https".equals(scheme)) {
                    return false;
                } else {
                    view.loadUrl(url);
                    return false;
                }
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                WebResourceResponse wrr = null;
                InputStream is = null;
                //这里处理url有点粗暴，之后优化
                String noSepPath=request.getUrl().toString();
                Log.e("noSepPath",noSepPath);
                String path="dist/index.html";
                if(noSepPath.endsWith(".html")
                        || noSepPath.endsWith(".htm")
                        || noSepPath.endsWith(".css")
                        || noSepPath.endsWith(".js")
                        || noSepPath.endsWith(".ico")
                        || noSepPath.endsWith(".png")
                        || noSepPath.endsWith(".gif")
                        || noSepPath.endsWith(".jpg")
                        ||noSepPath.endsWith(".webp")
                        || noSepPath.endsWith(".jpeg")){
//                    String temp= noSepPath.substring("http://webapp.1".length());
                    String temp= noSepPath.substring("qax://webapp.1".length());
                    Log.e("noSepPath","temp = "+ temp);
                    path="dist"+temp;
                    Log.e("noSepPath","path = "+ path);
                }
                try {
                    Log.e("path",path);
                    is = getResources().getAssets().open(path);
                } catch (IOException e) {
                    Log.e("path","IOException");
                    e.printStackTrace();
                }
                if(noSepPath.endsWith(".css")) {
                    wrr = new WebResourceResponse("text/css", "utf-8", is);
                }else if(noSepPath.endsWith(".js")) {

                       wrr = new WebResourceResponse( "application/x-javascript", "utf-8", is);

                }else if(noSepPath.endsWith(".png")) {
                    wrr = new WebResourceResponse("image/png", "utf-8", is);
                }
                else if(noSepPath.endsWith(".gif")) {
                    wrr = new WebResourceResponse("image/gif", "utf-8", is);
                }
                else if(noSepPath.endsWith(".jpg")) {
                    wrr = new WebResourceResponse("image/jpeg", "utf-8", is);
                }
                else if(noSepPath.endsWith(".jpeg")) {
                    wrr = new WebResourceResponse("image/jpeg", "utf-8", is);
                }else if(noSepPath.endsWith(".webp")) {
                    wrr = new WebResourceResponse("image/webp", "utf-8", is);
                }else{
//                    wrr = new WebResourceResponse("text/html", "utf-8", is);
                return null;
                }
                return  wrr;
//              return super.shouldInterceptRequest(view, wrr);
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.e("err","onReceivedError"+error.getDescription()+"url:"+request.getUrl());
                super.onReceivedError(view, request, error);
            }

        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = bundle.getString("title");
            String url = bundle.getString("url");
            //设置标题
            //setTitleText(title);
//            mWebView.loadUrl("https://www.baidu.com");
            mWebView.loadUrl(url);
        }
    }

    /**
     * @param context 上下文
     * @param title   activity标题
     * @param url     本地网页url
     */
    public static void startActivity(Context context, String title, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //挂在后台  资源释放
        mWebView.getSettings().setJavaScriptEnabled(false);
    }

    @Override
    protected void onDestroy() {
        mWebView.setVisibility(View.GONE);
        mWebView.destroy();
        super.onDestroy();
    }
}