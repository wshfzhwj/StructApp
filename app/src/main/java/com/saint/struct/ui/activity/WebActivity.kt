package com.saint.struct.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.saint.struct.R
import com.saint.struct.databinding.ActivityWebBinding
import java.io.IOException
import java.io.InputStream

class WebActivity : BaseActivity<ActivityWebBinding>() {
    private var mWebView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate")
        initView()
        initData()
    }

    override fun getViewBinding(): ActivityWebBinding {
        return ActivityWebBinding.inflate(layoutInflater)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e(TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e(TAG, "onRestoreInstanceState")
    }

    /**
     * 初始化视图资源
     */
    private fun initView() {
        mWebView = findViewById<View>(R.id.wb_vue) as WebView
    }

    @JavascriptInterface
    fun hello(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun testJS() {
        mWebView!!.loadUrl("javascript:test()")
    }

    /**
     * 加载初始数据
     */
    @SuppressLint("SetJavaScriptEnabled")
    protected fun initData() {
        val settings = mWebView!!.settings
        settings.let {
            it.javaScriptEnabled = true
            it.javaScriptCanOpenWindowsAutomatically = true
            it.setSupportMultipleWindows(true)
            it.domStorageEnabled = true
            it.databaseEnabled = true
            //        it.setSupportZoom(true);
//        it.setBuiltInZoomControls(true);
            it.useWideViewPort = true
            it.allowFileAccess = true
            it.loadWithOverviewMode = true
            it.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            it.allowContentAccess = true
            //  it.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        //  mWebView.addJavascriptInterface(this, "justTest");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            settings.setAllowFileAccessFromFileURLs(true);
//            settings.setAllowUniversalAccessFromFileURLs(true);
//        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        mWebView!!.webChromeClient = WebChromeClient()
        mWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                Log.e("noSepPath", "shouldOverrideUrlLoading==$url")
                val uri = Uri.parse(url)
                val scheme = uri.scheme
                Log.e("noSepPath", "uri.getScheme() ==$scheme")
                return if ("http" != scheme || "https" != scheme) {
                    false
                } else {
                    view.loadUrl(url)
                    false
                }
            }

            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? {
                var wrr: WebResourceResponse
                var `is`: InputStream? = null
                //这里处理url有点粗暴，之后优化
                val noSepPath = request.url.toString()
                Log.e("noSepPath", noSepPath)
                var path = "dist/index.html"
                if (noSepPath.endsWith(".html")
                    || noSepPath.endsWith(".htm")
                    || noSepPath.endsWith(".css")
                    || noSepPath.endsWith(".js")
                    || noSepPath.endsWith(".ico")
                    || noSepPath.endsWith(".png")
                    || noSepPath.endsWith(".gif")
                    || noSepPath.endsWith(".jpg")
                    || noSepPath.endsWith(".webp")
                    || noSepPath.endsWith(".jpeg")
                ) {
//                    String temp= noSepPath.substring("http://webapp.1".length());
                    val temp = noSepPath.substring("qax://webapp.1".length)
                    Log.e("noSepPath", "temp = $temp")
                    path = "dist$temp"
                    Log.e("noSepPath", "path = $path")
                }
                try {
                    Log.e("path", path)
                    `is` = resources.assets.open(path)
                } catch (e: IOException) {
                    Log.e("path", "IOException")
                    e.printStackTrace()
                }
                wrr = if (noSepPath.endsWith(".css")) {
                    WebResourceResponse("text/css", "utf-8", `is`)
                } else if (noSepPath.endsWith(".js")) {
                    WebResourceResponse("application/x-javascript", "utf-8", `is`)
                } else if (noSepPath.endsWith(".png")) {
                    WebResourceResponse("image/png", "utf-8", `is`)
                } else if (noSepPath.endsWith(".gif")) {
                    WebResourceResponse("image/gif", "utf-8", `is`)
                } else if (noSepPath.endsWith(".jpg")) {
                    WebResourceResponse("image/jpeg", "utf-8", `is`)
                } else if (noSepPath.endsWith(".jpeg")) {
                    WebResourceResponse("image/jpeg", "utf-8", `is`)
                } else if (noSepPath.endsWith(".webp")) {
                    WebResourceResponse("image/webp", "utf-8", `is`)
                } else {
//                    wrr = new WebResourceResponse("text/html", "utf-8", is);
                    return null
                }
                return wrr
                //              return super.shouldInterceptRequest(view, wrr);
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                Log.e("err", "onReceivedError" + error.description + "url:" + request.url)
                super.onReceivedError(view, request, error)
            }
        }
//        mWebView!!.loadUrl("https://www.baidu.com")
        mWebView!!.loadUrl("file:///android_asset/test.html")
        //        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            String title = bundle.getString("title");
//            String url = bundle.getString("url");
//            //设置标题
//            //setTitleText(title);
//
//            mWebView.loadUrl(url);
//        }

        mWebView!!.addJavascriptInterface(JsObject(), "JsObject")
        mWebView!!.addJavascriptInterface(JsObject2(), "JsObject")
        mWebView!!.addJavascriptInterface(object : Any() {
            @JavascriptInterface
            fun jsAndroid(msg: String) {
                Log.e(TAG, "JsObject  $msg");
            }
        }, "JsObject")
    }

    inner class JsObject {
        @JavascriptInterface
        fun jsAndroid(msg: String) {
            Log.e(TAG, "JsObject  $msg");
        }
    }

    inner class JsObject2 {
        @JavascriptInterface
        fun jsAndroid(msg: String) {
            Log.e(TAG, "JsObject2 $msg");
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onResume() {
        super.onResume()
        mWebView!!.settings.javaScriptEnabled = true
        mWebView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mWebView!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        //挂在后台  资源释放
        mWebView!!.settings.javaScriptEnabled = false
    }

    override fun onDestroy() {
        mWebView!!.visibility = View.GONE
        mWebView!!.destroy()
        super.onDestroy()
    }

    companion object {
        const val TAG = "WebActivity"

        /**
         * @param context 上下文
         * @param title   activity标题
         * @param url     本地网页url
         */
        fun startActivity(context: Context, title: String?, url: String?) {
            if (TextUtils.isEmpty(url)) {
                return
            }
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }
}