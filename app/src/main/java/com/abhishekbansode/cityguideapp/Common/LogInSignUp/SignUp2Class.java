package com.abhishekbansode.cityguideapp.Common.LogInSignUp;

import static com.abhishekbansode.cityguideapp.R.id.age_picker;

import android.content.Intent;
import android.os.Bundle;
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
        setContentView(R.layout.activity_sign_up2_class);

        // Hooks
        backBtn = findViewById(R.id.signup_back_button);
        nextBtn2 = findViewById(R.id.signup_next_button_page2);
        loginBtn = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(age_picker);

        /*
 calling SignUpScreen3 by next button
        nextBtn2.setOnClickListener(view -> {
            if(!validateAge() | !validateGender()) {
                return;
            }

            selectGender = findViewById(radioGroup.getCheckedRadioButtonId());
            String gender = selectGender.getText().toString();

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            String date = day + "/" + month + "/" + year;

            // Add transition
            Pair[] pairs = new Pair[4];
            pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
            pairs[1] = new Pair<View, String>(nextBtn2, "transition_next_btn");
            pairs[2] = new Pair<View, String>(loginBtn, "transition_login_btn");
            pairs[3] = new Pair<View, String>(titleText, "transition_title_text");

            Intent intent = new Intent(SignUp2Class.this, SignUp3Class.class);

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2Class.this, pairs);
            startActivity(intent, options.toBundle());

        });
*/

        // this intent is not working properly
        // as this does not navigating to SignUp3Class.java
        // so, the work flow is stopped for OTP screen and database also
        nextBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp2Class.this, SignUp3Class.class);
                startActivity(intent);
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
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