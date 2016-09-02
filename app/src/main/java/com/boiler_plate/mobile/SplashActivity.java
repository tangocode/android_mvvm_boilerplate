package com.boiler_plate.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.boiler_plate.mobile.view.mvvmSample.DummyFragmentActivity;


/**
 * This class represents the splash screen
 */

public class SplashActivity extends Activity {
    private int SPLASH_WAIT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(SPLASH_WAIT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, DummyFragmentActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
