package com.abhishekbansode.cityguideapp.Common.LogInSignUp;

import static com.abhishekbansode.cityguideapp.R.id.age_picker;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;

import java.util.Calendar;

public class SignUp2Class extends AppCompatActivity {


    // Variables
    ImageView backBtn;
    Button nextBtn2, loginBtn;
    TextView titleText;
    RadioGroup radioGroup;
    RadioButton selectGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_sign_up_2);

        // Hooks
        backBtn = findViewById(R.id.signup2_back_button);
        nextBtn2 = findViewById(R.id.signup_next_button_page2);
        loginBtn = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(age_picker);

        // Add transition
        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<>(nextBtn2, "transition_next_btn");
        pairs[2] = new Pair<>(loginBtn, "transition_login_btn");
        pairs[3] = new Pair<>(titleText, "transition_title_text");


//      calling SignUpScreen3 by next button
        nextBtn2.setOnClickListener(view -> {
            if(!validateAge() | !validateGender()) {
                Toast.makeText(this, "Something went wrong in signup screen 2", Toast.LENGTH_LONG).show();
                return;
            }

            selectGender = findViewById(radioGroup.getCheckedRadioButtonId());
            String gender = selectGender.getText().toString();

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            String date = day + "/" + month + "/" + year;

            Intent intent = new Intent(SignUp2Class.this, SignUp3Class.class);
//
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
            startActivity(intent, options.toBundle());

            startActivity(intent);

        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp2Class.this, SignUp.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp2Class.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateGender() {
        if(radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if(isAgeValid < 14) {
            Toast.makeText(this, "You are not eligible to apply!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}