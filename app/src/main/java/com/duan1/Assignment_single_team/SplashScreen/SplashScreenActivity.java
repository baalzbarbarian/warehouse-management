package com.duan1.Assignment_single_team.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.duan1.Assignment_single_team.LoginActivity.LoginActivity;
import com.duan1.Assignment_single_team.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread couldow = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }catch (Exception e){

                }finally {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        };
        couldow.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
