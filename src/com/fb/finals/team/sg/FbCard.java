package com.fb.finals.team.sg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FbCard implements UIContainer {
    
    private View view;
    private JsonNode json;
    
    public FbCard(Context context, String jsonString, FrameLayout baseView) {
        view = LayoutInflater.from(context).inflate(R.layout.content_fb, baseView, false);
        baseView.addView(view);
        try {
            json = new ObjectMapper().readTree(jsonString);
        } catch (Exception e) {
            Log.e("FbCard", "Couldn't parse JSON content");
        }
    }

    @Override
    public void renderUI() {
        ImageView profilePic = (ImageView) view.findViewById(R.id.imgProfilePic);
        String url = json.get("image").get("url").asText();
        ImageLoader.getInstance().displayImage(url, profilePic);
        
        TextView fullName = (TextView) view.findViewById(R.id.tvFullName);
        fullName.setText(json.get("title").asText());
    }

    @Override
    public String getTextForClipboard() {
        StringBuilder text = new StringBuilder();
        text.append(json.get("title").asText()).append(" - ");
        text.append(json.get("description").asText());
        return text.toString();
    }

}
