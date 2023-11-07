package com.abhishekbansode.cityguideapp.Common.LogInSignUp;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {

    // Variables
    ImageView screenIcon;
    TextView title, description;
    TextInputEditText phoneNumberTextField;
    CountryCodePicker countryCodePicker;
    Button nextBtn;
    ProgressBar progressBar;

    Button wayToResetPasswordScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        // new hooks
        screenIcon = findViewById(R.id.forget_password_icon);
        title = findViewById(R.id.forget_password_title);
        description = findViewById(R.id.forget_password_description);
        phoneNumberTextField = findViewById(R.id.forget_password_phone_number);
        countryCodePicker = findViewById(R.id.country_code_picker);
        nextBtn = findViewById(R.id.forget_password_next_btn);
//        progressBar = findViewById(R.id.progress_bar);

        // Animation Hook
//        animation = AnimationUtils.loadAnimation(this, R.anim.slide_animation);


        // Set animation to all the elements
//        screenIcon.setAnimation(animation);
//        title.setAnimation(animation);
//        description.setAnimation(animation);
//        phoneNumberTextField.setAnimation(animation);
//        countryCodePicker.setAnimation(animation);
//        nextBtn.setAnimation(animation);



    }
}