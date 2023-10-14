package com.abhishekbansode.cityguideapp.Common.LogInSignUp;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUp3Class extends AppCompatActivity {

    // variables
    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    // Get complete phone number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3_class);

        // Hooks
        phoneNumber = findViewById(R.id.phone_number);
        countryCodePicker = findViewById(R.id.country_code_picker);

        setContentView(R.layout.activity_sign_up3_class);
    }
}