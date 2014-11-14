package com.fb.finals.team.sg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FbCard implements UIContainer, OnClickListener {
    
    private View view;
    private JsonNode json;
    private String fbId;
    private Context context;
    
    public FbCard(Context context, String jsonString, FrameLayout baseView) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.content_fb, baseView, false);
        baseView.addView(view);
        try {
            json = new ObjectMapper().readTree(jsonString);
            fbId = json.get("facebookID").asText();
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
        
        view.findViewById(R.id.fbLogo).setOnClickListener(this);
        
        //Current location stuff
        if (json.has("currentLocation")) {
            TextView currentLocation = (TextView) view.findViewById(R.id.tvCurrentLocation);
            currentLocation.setText(json.get("currentLocation").asText());    
        } else {
            view.findViewById(R.id.tvCurrentLocationLabel).setVisibility(View.GONE);
            view.findViewById(R.id.tvCurrentLocation).setVisibility(View.GONE);
        }
        
        //Hometown stuff
        if (json.has("hometown")) {
            TextView hometown = (TextView) view.findViewById(R.id.tvFrom);
            hometown.setText(json.get("hometown").asText());
        } else {
            view.findViewById(R.id.tvFromLabel).setVisibility(View.GONE);
            view.findViewById(R.id.tvFrom).setVisibility(View.GONE);
        }
    }

    @Override
    public String getTextForClipboard() {
        StringBuilder text = new StringBuilder();
        text.append(json.get("title").asText()).append(" - ");
        text.append(json.get("description").asText());
        return text.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fbLogo:
                sendUserToFb();
                break;
        }
    }
    
    private void sendUserToFb() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + fbId));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
