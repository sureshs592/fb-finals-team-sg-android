package com.fb.finals.team.sg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MovieCards {
    
    private View view;
    private JsonNode json;
    
    public MovieCards(Context context, String jsonString, FrameLayout baseView) {
        view = LayoutInflater.from(context).inflate(R.layout.content_movie, baseView);
        baseView.addView(view);
        try {
            json = new ObjectMapper().readTree(jsonString);
        } catch (Exception e) {
            Log.e("MovieCards", "Couldn't parse JSON content");
        }
    }

    public void renderUI() {
        TextView tvMovieTitle = (TextView) view.findViewById(R.id.tvMovieTitle);
        tvMovieTitle.setText(json.get("title").asText());
    }

}
