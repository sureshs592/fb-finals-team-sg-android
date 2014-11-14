package com.fb.finals.team.sg;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class TestActivity extends Activity {
    
    private GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        registerDeviceForGCM();
        
        Intent serviceToStart = new Intent(this, UIService.class);
        serviceToStart.putExtra("type", "movie");
        serviceToStart.putExtra("payload", "{ title: 'blah blah blah' }");
        startService(serviceToStart);
    }
    
    private void registerDeviceForGCM() {
        gcm = GoogleCloudMessaging.getInstance(this);
        new RegisterGCM().execute();
    }
    
    private class RegisterGCM extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String msg = "";
            try {
                String regid = gcm.register("296670291727");
                msg = "Device registered, registration ID=" + regid;
                
            } catch (IOException ex) {
                msg = "Error :" + ex.getMessage();
                // If there is an error, don't just keep trying to register.
                // Require the user to click a button again, or perform
                // exponential back-off.
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            Log.v("GCM", msg);
        }
    }

}
