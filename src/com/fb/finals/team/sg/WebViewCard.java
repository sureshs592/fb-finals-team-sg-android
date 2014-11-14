package com.fb.finals.team.sg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebViewCard {
    
    private View view;
    private JsonNode json;
    
    public WebViewCard(Context context, String jsonString, FrameLayout baseView) {
        view = LayoutInflater.from(context).inflate(R.layout.content_webview, baseView, false);
        baseView.addView(view);
        try {
            json = new ObjectMapper().readTree(jsonString);
        } catch (Exception e) {
            Log.e("WebViewCard", "Couldn't parse JSON content");
        }
    }

    public void renderUI() {
        String url = json.get("url").asText();
        WebView webView = (WebView) view.findViewById(R.id.webview);
        webView.loadUrl(url);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
    }

}
