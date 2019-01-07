package com.example.gamal.adnp7_capstoneproject.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.gamal.adnp7_capstoneproject.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO start the login activity
                Intent intent=new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        try {
            thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.run();

    }
}
