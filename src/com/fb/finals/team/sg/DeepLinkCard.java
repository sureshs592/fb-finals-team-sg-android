package com.fb.finals.team.sg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeepLinkCard implements OnClickListener {
    
    private View view;
    private JsonNode json;
    private String url;
    private Context context;
    
    public DeepLinkCard(Context context, String jsonString, FrameLayout baseView) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.content_deeplink, baseView, false);
        baseView.addView(view);
        try {
            json = new ObjectMapper().readTree(jsonString);
            url = json.get("url").asText();
        } catch (Exception e) {
            Log.e("DeepLinkCard", "Couldn't parse JSON content");
        }
    }

    public void renderUI() {
        view.findViewById(R.id.tvLabel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLabel:
                startIntent();
                break;
        }
    }
    
    private void startIntent() {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
