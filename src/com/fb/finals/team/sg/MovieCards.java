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

public class MovieCards implements UIContainer {
    
    private View view;
    private JsonNode json;
    
    public MovieCards(Context context, String jsonString, FrameLayout baseView) {
        view = LayoutInflater.from(context).inflate(R.layout.content_movie, baseView, false);
        baseView.addView(view);
        try {
            json = new ObjectMapper().readTree(jsonString);
        } catch (Exception e) {
            Log.e("MovieCards", "Couldn't parse JSON content");
        }
    }

    @Override
    public void renderUI() {
        //Cover image stuff
        String url = json.get("image").get("url").asText();
//        String url = json.get("url").asText();
        ImageView poster = (ImageView) view.findViewById(R.id.imgPoster);
        ImageLoader.getInstance().displayImage(url, poster);
        
        //Title + description cards
        TextView tvMovieTitle = (TextView) view.findViewById(R.id.tvMovieTitle);
        TextView tvMovieDescription = (TextView) view.findViewById(R.id.tvMovieDescription);
        
        tvMovieTitle.setText(json.get("title").asText());
        tvMovieDescription.setText(json.get("description").asText());
    }

    @Override
    public String getTextForClipboard() {
        StringBuilder text = new StringBuilder();
        text.append(json.get("title").asText()).append(" - ");
        text.append(json.get("description").asText());
        return text.toString();
    }

}
