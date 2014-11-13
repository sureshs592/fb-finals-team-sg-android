package com.fb.finals.team.sg;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UIService extends Service {
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO need to write code to create the UI
        
        return START_NOT_STICKY;
    }

    /**
     * UNUSED
     */
    @Override
    public IBinder onBind(Intent arg0) {
        //We're not using this
        return null;
    }

}
