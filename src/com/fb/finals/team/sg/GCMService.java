package com.fb.finals.team.sg;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.Set;

public class GCMService extends IntentService {

    public GCMService() {
        super("GCMService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        Log.v("GCM", "*** GCM message received ***");

        Bundle argsToSend = new Bundle();
        argsToSend.putString("type", extras.getString("type"));
        argsToSend.putString("payload", extras.getString("payload"));
        
        Intent uiService = new Intent(this, UIService.class);
        uiService.putExtras(argsToSend);
        startService(uiService);
        
        //Release the wake lock provided by the WakefulBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }


}