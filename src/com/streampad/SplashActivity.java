package com.streampad;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {
    private long splashDelay = 1500;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();
                Intent hackbookIntent = new Intent().setClass(SplashActivity.this, StreamPad.class);
                startActivity(hackbookIntent);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, splashDelay);
    }
}