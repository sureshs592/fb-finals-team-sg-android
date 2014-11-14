package com.fb.finals.team.sg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UIService extends Service implements OnClickListener {
    
    private WindowManager windowManager;
    private View overlay, bubble, drawer;
    private TextView tvTitle;
    private FrameLayout frameContent;
    private UIContainer uiContainer;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Clear existing information from the drawer. Setup UI if required
        boolean viewsExist = clearExistingInfo();
        if (!viewsExist) inflateAndSetupUI(); 
        
        //Read information from the push notification
        Bundle extras = intent.getExtras();
        String type = extras.getString("type");
        String payload = extras.getString("payload");
        Log.v("payload", payload);
        
        chooseUIToDisplay(type, payload);
        displayHeader(extras.getString("title"));
        
        return START_NOT_STICKY;
    }
    
    private boolean clearExistingInfo() {
        if (frameContent != null) {
            frameContent.removeAllViews();
            return true;
        }
        
        return false;
    }
    
    private void inflateAndSetupUI() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        overlay = inflater.inflate(R.layout.overlay_section, null);
        
        bubble = overlay.findViewById(R.id.btnBubble);
        drawer = overlay.findViewById(R.id.llDrawer);
        tvTitle = (TextView) overlay.findViewById(R.id.tvTitle);
        frameContent = (FrameLayout) overlay.findViewById(R.id.frameContent);
        bubble.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        overlay.findViewById(R.id.btnClose).setOnClickListener(this);
        overlay.findViewById(R.id.btnCollapse).setOnClickListener(this);
        overlay.findViewById(R.id.btnCopy).setOnClickListener(this);
        
        WindowManager.LayoutParams lp = new LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        lp.gravity = Gravity.TOP | Gravity.LEFT;
        lp.x = 0;
        lp.y = 100;
        
        windowManager.addView(overlay, lp);
    }
    
    private void displayHeader(String title) {
        if (!bubble.isShown()) {
            bubble.setAlpha(0.0f);
            bubble.setVisibility(View.VISIBLE);
            
            bubble.animate()
            .alpha(1f)
            .setDuration(getResources().getInteger(
                    android.R.integer.config_shortAnimTime))
            .setListener(null);
        }
        if (title == null || title.isEmpty()) {
            return;
        }
        
        tvTitle.setText(title);
        tvTitle.setAlpha(0.0f);
        tvTitle.setVisibility(View.VISIBLE);
        
        tvTitle.animate()
            .alpha(1f)
            .setDuration(getResources().getInteger(
                    android.R.integer.config_shortAnimTime))
            .setListener(null);
        
        tvTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvTitle.animate()
                .alpha(0f)
                .setDuration(getResources().getInteger(
                        android.R.integer.config_shortAnimTime))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tvTitle.setVisibility(View.GONE);
                    }
                });
            }
        }, 5000);
    }
    
    private void chooseUIToDisplay(String type, String payload) {
        if (type.equalsIgnoreCase("movie")) {
            uiContainer = new MovieCards(this, payload, frameContent);
        } else if (type.equalsIgnoreCase("url")) {
            uiContainer = new WebViewCard(this, payload, frameContent);
        } else if (type.equals("person")) {
            uiContainer = new FbCard(this, payload, frameContent);
        }
        
        uiContainer.renderUI();
    }

    /**
     * UNUSED. We are not using this stuff
     */
    @Override
    public IBinder onBind(Intent arg0) { return null; }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTitle:
            case R.id.btnCollapse:
            case R.id.btnBubble:
                toggleDrawer();
                break;
            case R.id.btnClose:
                destroyOverlay();
                break;
            case R.id.btnCopy:
                copyText();
                break;
        }
    }
    
    private void toggleDrawer() {
        boolean isVisible = drawer.isShown();
        drawer.setVisibility((isVisible) ? View.GONE : View.VISIBLE);
    }
    
    private void destroyOverlay() {
        windowManager.removeView(overlay);
        
        bubble = null;
        drawer = null;
        uiContainer = null;
        frameContent = null;
        overlay = null;
    }
    
    private void copyText() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("info", uiContainer.getTextForClipboard());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Copied to clipboard!", Toast.LENGTH_SHORT).show();
        toggleDrawer();
    }

}
