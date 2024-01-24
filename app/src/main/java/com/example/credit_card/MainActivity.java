package com.example.credit_card;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import wendu.dsbridge.DWebView;

public class MainActivity extends AppCompatActivity {
    WebSettings webSettings;
    DWebView webView;
    String url;

    Context context;

    boolean clearHistory = true;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = "https://app.federal.k8s.stage.mintpro.in/api/tenant-leads/state?enqp=XjdBU7HgUx2I%2BTakUuOwueRlELjQ9XXh9hYL7SVqxKu026S4admThMIuRU%2FW5EvSIqHN7YCNH%2BhTnFVNaA80fHqdMT8JIWRpkZlUMqMCSPh051WmGWRtiJ%2BJEJj%2BeYuJFLDc0FrFq%2FuWZZ2N0RfOLqYKeqsguArgSU4BjfwMHIUR%2FpjGd%2BdM2S4dWpdGFNMMyytfIvGRl5bZNpbX0s6F0oE4Ak6yB7LWi%2F0ZuWB9Zh%2FqcsY%2BViZ1wVMuikerHeHq";
//        url = "https://ally-dev.danubehome.com/om/en?paymentSource=nuclei";
//        url = "https://app.federal.k8s.stage.mintpro.in/";
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        }, 1000);

        webView = findViewById(R.id.webView);

//        webView.setWebContentsDebuggingEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); // Load the URL in the WebView
                return true;
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(WebView view, String weburl) {
//                webView.clearHistory();
                super.onPageFinished(view, url);

                String testUrl = "/cardstack/dashboard-screen?type=CREDIT&flow=PAYMENT";
                Uri parsedUri = Uri.parse(testUrl);
                Log.d("Testing URI", "pending path: " + parsedUri.getPath().replace("/cardstack", "") + "?" + parsedUri.getQuery());
                Log.d("Testing URI", "pending path: " + parsedUri.getQueryParameter("flow"));

//                Log.d("Testing URI", "path: " + parsedUri.getPath());

                progressBar.setVisibility(View.GONE);
                webView.evaluateJavascript("javascript:testFuncFromNative()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        if (!TextUtils.isEmpty(s) && !s.equalsIgnoreCase("null")) {
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Log.d("AjayMahadevan"," " + errorResponse.getStatusCode());
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
//                DWebView newWebView = new DWebView(view.getContext());
//                newWebView.setWebViewClient(new WebViewClient() {
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                        view.loadUrl(url);
//                        return true;
//                    }
//                });
//
//                DWebView.WebViewTransport transport = (DWebView.WebViewTransport) resultMsg.obj;
//                transport.setWebView(newWebView);
//                resultMsg.sendToTarget();

                DWebView.WebViewTransport transport = (DWebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(webView);
                resultMsg.sendToTarget();

                return true;
            }
        });
        context = this;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.loadUrl(url);
        Handler handler =  new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DeepLinkHandler.openLink(context,"federalapp://federalapp/home/loan");
            }
        }, 5000);

//        new CountDownTimer(20000, 1000){
//            @Override
//            public void onTick(long millisUntilFinished) {
//                Log.d("CountDownTimer", "" + millisUntilFinished);
//            }
//
//            @Override
//            public void onFinish() {
//                Log.d("CountDownTimer", "2 mins timer elapsed");
//            }
//        }.start();
    }

    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }
}















