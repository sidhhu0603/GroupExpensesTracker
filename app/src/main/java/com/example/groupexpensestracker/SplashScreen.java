package com.example.groupexpensestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    ImageView img1,img2;
    TextView txt1;
    LottieAnimationView lottie1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        img1 = findViewById(R.id.splashbg);
        img2 = findViewById(R.id.logo);
        txt1 = findViewById(R.id.textname);
        lottie1 = findViewById(R.id.lottie);


        img1.animate().translationY(-2500).setDuration(1000).setStartDelay(4000);
        img2.animate().translationY(1600).setDuration(1000).setStartDelay(4000);
        txt1.animate().translationY(1500).setDuration(1000).setStartDelay(4000);
        lottie1.animate().translationY(1500).setDuration(1000).setStartDelay(4000);

        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(7000);
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        };
        timer.start();

    }
}