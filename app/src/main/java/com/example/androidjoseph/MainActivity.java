package com.example.androidjoseph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //place what should happen after timer is over
                Intent intent = new Intent(MainActivity.this,AuthenticationScreen.class);
                startActivity(intent);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}