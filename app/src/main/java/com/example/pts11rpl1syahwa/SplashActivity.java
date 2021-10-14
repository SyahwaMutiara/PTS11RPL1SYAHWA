package com.example.pts11rpl1syahwa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 3 seconds
                    sleep(3 * 1000);
                    Intent i = new Intent(getBaseContext(), DashboardActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                }
            }
        };

        background.start();

    }
}