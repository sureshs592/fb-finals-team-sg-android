package com.fb.finals.team.sg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Prepare a dummy payload and trigger the service.
        //This will eventually by replaced. The service will be triggered through GCM.
        String testText = "This is a dummy payload to displayed on the phone";
        
        Intent serviceToStart = new Intent(this, UIService.class);
        serviceToStart.putExtra("text", testText);
        startService(serviceToStart);
    }

}
