package com.mazaiting.freemarkertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) this.findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptEnabled(true);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user", "mazaiting");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mWebView.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public String getString() {
                return jsonObject.toString();
            }
        }, "java");
        try {
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    String url = "file:///android_asset/main.html";
                    mWebView.loadUrl(url);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
