package com.abhishekbansode.cityguideapp.Common.LogInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;


public class SignUp2ndClass extends AppCompatActivity {

    // variables
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2nd_class);

        nextBtn = findViewById(R.id.signup_next_button_page2_test);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp3Class.class);
                startActivity(intent);
            }
        });

    }


    // just testing purpose
    public void TestingNextScreen(View view) {
        Intent intent = new Intent(this, VerifyOTP.class);
        startActivity(intent);
    }
}