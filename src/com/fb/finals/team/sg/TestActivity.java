package com.fb.finals.team.sg;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestActivity extends Activity {
    
    private GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        registerDeviceForGCM();
        
        Intent serviceToStart = new Intent(this, UIService.class);
        serviceToStart.putExtra("type", "fb");
//        serviceToStart.putExtra("payload", "{ \"title\": \"blah blah blah\", \"description\":\"asdf asdf asdf \", \"image\": { \"url\": \"http://content6.flixster.com/movie/11/17/96/11179632_tmp.jpg\"} }");
//        serviceToStart.putExtra("payload", "{ \"url\": \"https://www.youtube.com/watch?v=0rhwc5_iWkA\" }");
//        serviceToStart.putExtra("payload", "{ \"url\": \"https://www.yahoo.com/movies/film/interstellar\" }");
//        serviceToStart.putExtra("payload", "{ \"url\": \"https://www.google.com/maps/place/1+Hacker+Way,+Menlo+Park\" }");
//        serviceToStart.putExtra("payload", "{ \"url\": \"https://p.scdn.co/mp3-preview/f454c8224828e21fa146af84916fd22cb89cedc6\" }");
        serviceToStart.putExtra("payload", "{ \"image\": { \"url\": \"http://graph.facebook.com/218471/picture?height=200\" }, \"title\": \"Paul Tarjan\", \"currentLocation\": \"Lives in Palo Alto, California\", \"hometown\": \"From Calgary, Alberta\" } ");
        startService(serviceToStart);
        
//        final Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                startService("movie", "{ \"title\": \"blah blah blah\", \"description\":\"asdf asdf asdf \", \"image\": { \"url\": \"http://content6.flixster.com/movie/11/17/96/11179632_tmp.jpg\"} }");
//            }
//        });
//        
//        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
//        executor.schedule(new Runnable() {
//                @Override
//                public void run() {
//                    t.start();
//                }
//        }, 10, TimeUnit.SECONDS);
    }
    
    private void startService(String type, String payload) {
        Intent serviceToStart = new Intent(this, UIService.class);
        serviceToStart.putExtra("type", type);
        serviceToStart.putExtra("payload", payload);
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
