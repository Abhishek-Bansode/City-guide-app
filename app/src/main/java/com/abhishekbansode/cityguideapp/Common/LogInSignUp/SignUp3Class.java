package com.abhishekbansode.cityguideapp.Common.LogInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;
import com.hbb20.CountryCodePicker;

public class SignUp3Class extends AppCompatActivity {

    // variables
    EditText phoneNumber;

    CountryCodePicker ccp;
    Button nextBtn3, loginBtn;

    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_sign_up_3);

        // Bind CCP and Carrier Number editText from layout
        ccp = (CountryCodePicker) findViewById(R.id.cpp);
        phoneNumber = (EditText) findViewById(R.id.signup_phone_number);

        // Attach CarrierNumber editText to CCP
        ccp.registerCarrierNumberEditText(phoneNumber);

        // Hooks
        loginBtn = findViewById(R.id.signup3_login_button);
        nextBtn3 = findViewById(R.id.signup_next_button_page3);
        backBtn = findViewById(R.id.signup3_back_button);


        // on click listeners
        nextBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ccp.isValidFullNumber()) {
                    phoneNumber.setError("Invalid phone number");
                    return;

                    // Phone number is valid, proceed to the next screen
//                    Intent intent = new Intent(SignUp3Class.this, VerifyOTP.class);
//                    startActivity(intent);intent
                }

                // get all values passed from previous screen using Intent
                String _fullName = getIntent().getStringExtra("fullName");
                String _email = getIntent().getStringExtra("email");
                String _username = getIntent().getStringExtra("username");
                String _password = getIntent().getStringExtra("password");
                String _date = getIntent().getStringExtra("date");
                String _gender = getIntent().getStringExtra("gender");


                //get unformatted number with prefix "+" i.e "+14696641766"
                final String _phoneNo = ccp.getFullNumberWithPlus();

                Toast.makeText(SignUp3Class.this, _phoneNo, Toast.LENGTH_SHORT).show();


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

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp3Class.this, SignUp2Class.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp3Class.this, Login.class);
                startActivity(intent);
            }
        });
    }
}