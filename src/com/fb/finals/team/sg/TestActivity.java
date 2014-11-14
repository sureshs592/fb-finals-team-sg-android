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
        serviceToStart.putExtra("type", "url");
//        serviceToStart.putExtra("payload", "{ \"title\": \"blah blah blah\", \"description\":\"asdf asdf asdf \", \"image\": { \"url\": \"http://content6.flixster.com/movie/11/17/96/11179632_tmp.jpg\"} }");
        serviceToStart.putExtra("payload", "{ \"url\": \"http://www.google.com/movies?q=interstellar+timings&mid=cc7a8a0cd9f1b62&hl=en&oi=showtimes&ct=change-location&near=menlo+park\" }");
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
