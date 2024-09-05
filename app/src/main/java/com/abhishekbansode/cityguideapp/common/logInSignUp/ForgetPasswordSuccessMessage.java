package com.abhishekbansode.cityguideapp.common.logInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;

public class ForgetPasswordSuccessMessage extends AppCompatActivity {

    // Variables
    Button loginBtn;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_success_message);

        // Hooks
        loginBtn = findViewById(R.id.password_updated_login_btn);
        backBtn = findViewById(R.id.password_updated_backBtn);

        // Navigate to Login Screen
        loginBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        });

        // Navigate the user Retailer StartUpScreen
        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
            finish();
        });
    }
}