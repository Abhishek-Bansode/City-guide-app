package com.abhishekbansode.cityguideapp.Common.LogInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp3Class extends AppCompatActivity {

    // variables
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    Button nextBtn3;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3_class);

        // Hooks
        phoneNumber = findViewById(R.id.signup_phone_number);
        countryCodePicker = findViewById(R.id.country_code_picker);
        nextBtn3 = findViewById(R.id.signup_next_button_page3);
        backBtn = findViewById(R.id.signup_back_button);

        nextBtn3.setOnClickListener(view -> {
            // validate fields
            if(validatePhoneNumber(String.valueOf(phoneNumber))) {
                return;
            } // validation succeeded and now move to next screen to verify phone number and save data

            // get all values passed from previous screen using Intent
            String _fullName = getIntent().getStringExtra("fullName");
            String _email = getIntent().getStringExtra("email");
            String _username = getIntent().getStringExtra("username");
            String _password = getIntent().getStringExtra("password");
            String _date = getIntent().getStringExtra("date");
            String _gender = getIntent().getStringExtra("gender");

            // Get complete phone number
            String _getUserEnteredPhoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
            // Remove first zero if entered
            if(_getUserEnteredPhoneNumber.charAt(0) == '0') {
                _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
            }
            final String _phoneNo = countryCodePicker.getFullNumberWithPlus() + _getUserEnteredPhoneNumber;

            Intent intent = new Intent(SignUp3Class.this, VerifyOTP.class);

            // pass all fields to the next activity
            intent.putExtra("fullName", _fullName);
            intent.putExtra("email", _email);
            intent.putExtra("username", _username);
            intent.putExtra("password", _password);
            intent.putExtra("date", _date);
            intent.putExtra("gender", _gender);
            intent.putExtra("phoneNo", _phoneNo);
            intent.putExtra("whatToDO", "createNewUser"); // This is to identify that which action should OTP perform after verification.

            startActivity(intent);
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp3Class.this, SignUp2Class.class);
                startActivity(intent);
            }
        });
    }

    // Get complete phone number
    String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();

    final String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;


//    public void callVerifyOTPScreen(View view) {
//
//        // validate fields
//        if(validatePhoneNumber(_phoneNo)) {
//            return;
//        } // validation succeeded and now move to next screen to verify phone number and save data
//
//        // get all values passed from previous screen using Intent
//        String _fullName = getIntent().getStringExtra("fullName");
//        String _email = getIntent().getStringExtra("email");
//        String _username = getIntent().getStringExtra("username");
//        String _password = getIntent().getStringExtra("password");
//        String _date = getIntent().getStringExtra("date");
//        String _gender = getIntent().getStringExtra("gender");
//
//
//        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
//
//        // pass all fields to the next activity
//        intent.putExtra("fullName", _fullName);
//        intent.putExtra("email", _email);
//        intent.putExtra("username", _username);
//        intent.putExtra("password", _password);
//        intent.putExtra("date", _date);
//        intent.putExtra("gender", _gender);
//        intent.putExtra("phoneNo", _phoneNo);
//        intent.putExtra("whatToDO", "createNewUser"); // This is to identify that which action should OTP perform after verification.
//
//        startActivity(intent);
//    }
    private static final String PHONE_NUMBER_PATTERN = "^\\+(?:[0-9] ?){6,14}[0-9]$"; // Assumes a 10-digit phone number
    /**
     * (0/91): number starts with (0/91)
     * [7-9]: starting of the number may contain a digit between 0 to 9
     * [0-9]: then contains digits 0 to 9
     */

    private boolean validatePhoneNumber(String phoneNumber) {
        // Compile the regular expression pattern
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

        // Match the input phone number against the pattern
        Matcher matcher = pattern.matcher(phoneNumber);

        return !matcher.matches();
    }

//    private boolean validatePhoneNumber() {
//        String val = phoneNumber.getEditText().getText().toString().trim();
//        String checkspaces = "Aw{1,20}z";
//        if (val.isEmpty()) {
//            phoneNumber.setError("Enter valid phone number");
//            return false;
//        } else if (!val.matches(checkspaces)) {
//            phoneNumber.setError("No White spaces are allowed!");
//            return false;
//        } else {
//            phoneNumber.setError(null);
//            phoneNumber.setErrorEnabled(false);
//            return true;
//        }
//    }

}