package com.abhishekbansode.cityguideapp.Common.LogInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;

public class ForgetPassword extends AppCompatActivity {

    Button wayToResetPasswordScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        // hooks
        wayToResetPasswordScreen=findViewById(R.id.wayToForget);

        wayToResetPasswordScreen.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(),MakeSelection.class);
            startActivity(intent);
        });
    }
}