package com.abhishekbansode.cityguideapp.Common.LogInSignUp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    // Variables
    TextInputLayout fullName, userName, email, password;
    ImageView backBtn;
    Button nextBtn1, loginBtn;
    TextView titleText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_sign_up);

        // hooks
        backBtn = findViewById(R.id.signup1_back_button);
        nextBtn1 = findViewById(R.id.signup_next_button_page1);
        loginBtn = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);

        // finding their id's from XML and put it here (remaining)
        fullName = findViewById(R.id.signup_fullname);
        userName = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email2);
        password = findViewById(R.id.signup_password);


        // Add transition
        Pair[] pairs = new Pair[4];

        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(nextBtn1, "transition_next_btn");
        pairs[2] = new Pair<View, String>(loginBtn, "transition_login_btn");
        pairs[3] = new Pair<View, String>(titleText, "transition_title_text");


        /*
        *  nextBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateFullName() | !validateEmail() | !validateUsername() | !validatePassword()) {
                    return;
                }
        *
        * */


        // calling SignUpScreen2 by next button
        nextBtn1.setOnClickListener(view -> {
            if(!validateFullName() | !validateEmail() | !validateUsername() | !validatePassword()) {
                return;
            }
            Intent intent = new Intent(SignUp.this, SignUp2Class.class);

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
            startActivity(intent, options.toBundle());
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RetailerStartUpScreen.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateFullName() {
        String val = Objects.requireNonNull(fullName.getEditText()).getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = Objects.requireNonNull(userName.getEditText()).getText().toString().trim();
        String checkspaces = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            userName.setError("Field can not be Empty");
            return false;
        } else if (val.length() >= 15) {
            userName.setError("Username is too long!");
            return false;
        } else if (!val.matches(checkspaces)) {
            userName.setError("No whitespaces are allowed!");
            return false;
        } else {
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("Field can not be Empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 4 characters!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}