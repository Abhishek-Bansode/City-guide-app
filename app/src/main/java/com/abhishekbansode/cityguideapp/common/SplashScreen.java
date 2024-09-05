package com.abhishekbansode.cityguideapp.common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;
import com.abhishekbansode.cityguideapp.user.UserDashboard;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_TIMER = 5000;

    // variables
    ImageView backgroundImage;
    TextView poweredByLine;

    // Animations
    Animation sideAnim, bottomAnim;
    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // some code from yt
        // one more thing is that, before doing this , styles.xml file also had changed

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        //Hooks
        backgroundImage = findViewById(R.id.background_img);
        poweredByLine = findViewById(R.id.powered_by);

        // Animations
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.slide_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        // set animations on elements
        backgroundImage.setAnimation(sideAnim);
        poweredByLine.setAnimation(bottomAnim);

        // get eyes on this line , it is very imp for now
        new Handler().postDelayed(this::run,SPLASH_TIMER);

    }

    private void run() {

        onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
        boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

        if(isFirstTime) {
            SharedPreferences.Editor editor = onBoardingScreen.edit();
            editor.putBoolean("firstTime",false);
//                editor.commit();
            editor.apply(); // if didn't worked then uncomment the above line

            Intent intent = new Intent(SplashScreen.this, OnBoarding.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(SplashScreen.this, UserDashboard.class);
            startActivity(intent);
            finish();
        }

    }
}