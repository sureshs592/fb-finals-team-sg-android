package com.fb.finals.team.sg;

import android.view.View;
import android.widget.TextView;

public class MovieCards {
    
    private View view;
    
    public MovieCards(View baseView) {
        this.view = baseView;
    }

    public void renderUI() {
        
    }
    
    private void renderInfoCard() {
        TextView tvMovieTitle = (TextView) view.findViewById(R.id.tvMovieTitle);
    }

}
