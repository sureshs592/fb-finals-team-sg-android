package com.fb.finals.team.sg;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class UIService extends Service implements OnClickListener {
    
    private WindowManager windowManager;
    private View overlay, bubble, drawer;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Read information from the push notification
        Bundle extras = intent.getExtras();
        String type = extras.getString("type");
        String payload = extras.getString("payload");
        
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        overlay = inflater.inflate(R.layout.overlay_section, null);
        
        bubble = overlay.findViewById(R.id.btnBubble);
        drawer = overlay.findViewById(R.id.llDrawer);
        
        bubble.setOnClickListener(this);
        overlay.findViewById(R.id.btnClose).setOnClickListener(this);
        
        WindowManager.LayoutParams lp = new LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        lp.gravity = Gravity.TOP | Gravity.LEFT;
        lp.x = 10;
        lp.y = 100;
        
        windowManager.addView(overlay, lp);
        
        return START_NOT_STICKY;
    }

    /**
     * UNUSED. We are not using this stuff
     */
    @Override
    public IBinder onBind(Intent arg0) { return null; }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBubble:
                toggleDrawer();
                break;
            case R.id.btnClose:
                destroyOverlay();
        }
    }
    
    private void toggleDrawer() {
        boolean isVisible = drawer.isShown();
        drawer.setVisibility((isVisible) ? View.GONE : View.VISIBLE);
    }
    
    private void destroyOverlay() {
        windowManager.removeView(overlay);
    }

}
