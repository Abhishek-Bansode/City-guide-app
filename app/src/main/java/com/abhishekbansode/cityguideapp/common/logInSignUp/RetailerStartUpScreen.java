package com.abhishekbansode.cityguideapp.common.logInSignUp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;

public class RetailerStartUpScreen extends AppCompatActivity {
    Button logIn, signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_start_up_screen);

        // Hooks
        signUp = findViewById(R.id.signUp_btn);
        logIn = findViewById(R.id.login_btn);

        // on click actions
        logIn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                    (RetailerStartUpScreen.this, android.util.Pair.create(logIn, "transition_login"));

            // Start the new activity
            startActivity(intent, options.toBundle());
        });

        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);

            // new way to do this - transition animation
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                    (RetailerStartUpScreen.this, android.util.Pair.create(signUp, "transition_login"));

            // Start the new activity
            startActivity(intent, options.toBundle());
        });
    }
}