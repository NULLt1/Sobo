package com.example.liebherr_365_gesundheitsapp.SplashScreen;

/**
 * Created by Melissa Hug on 18.05.2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.liebherr_365_gesundheitsapp.MainMenu;
import com.example.liebherr_365_gesundheitsapp.R;


public class Splash extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash_screen);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(500);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(Splash.this, MainMenu.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }
    protected void onPause(){
        super.onPause();
        finish();
    }
}