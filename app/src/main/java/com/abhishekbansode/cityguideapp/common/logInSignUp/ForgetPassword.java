package com.abhishekbansode.cityguideapp.common.logInSignUp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class ForgetPassword extends AppCompatActivity {

    // Variables
    ImageView screenIcon, backBtn;
    TextView title, description;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    Button nextBtn;
    Animation animation;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        // new hooks
        screenIcon = findViewById(R.id.forget_password_icon);
        title = findViewById(R.id.forget_password_title);
        description = findViewById(R.id.forget_password_description);
        phoneNumber = findViewById(R.id.forget_password_phone_number);
        countryCodePicker = findViewById(R.id.cpp);
        nextBtn = findViewById(R.id.forget_password_next_btn);
        backBtn = findViewById(R.id.forget_password_backBtn);
        progressBar = findViewById(R.id.forget_password_progress_bar);

        // Animation Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.slide_animation);

        // Set animation to all the elements
        screenIcon.setAnimation(animation);
        title.setAnimation(animation);
        description.setAnimation(animation);
        phoneNumber.setAnimation(animation);
        countryCodePicker.setAnimation(animation);
        nextBtn.setAnimation(animation);

        // call the OTP Screen and pass the phoneNo for verification
        nextBtn.setOnClickListener(view -> {

            // check the internet connection
            if (!isConnected((ForgetPassword) getApplicationContext())) {
                showCustomDialog();
            }

            // validate phone number
            if (!validateFields()) {
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            // Get data from fields
            String _phoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();

            if (_phoneNumber.charAt(0) == '0') {
                _phoneNumber = _phoneNumber.substring(1);
            }
            String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;

            // Check whether User exists or not in Database
            Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // If phone number exists then call OTP screen to verify that it is his number
                    if (snapshot.exists()) {
                        phoneNumber.setError(null);
                        phoneNumber.setErrorEnabled(false);

                        // Verify the User using OTP and update the Database
                        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                        intent.putExtra("phoneNo", _completePhoneNumber);
                        intent.putExtra("whatToDo", "updateData");

                        startActivity(intent);
                        finish();

                        progressBar.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.GONE);

                        phoneNumber.setError("No such User exists!");
                        phoneNumber.requestFocus();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgetPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
            finish();
        });
    }

    private boolean validateFields() {
        String _phoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Phone number can not be empty");
            phoneNumber.requestFocus();
            return false;
        } else {
            // condition is remaining
            CharSequence text = "There's nothing to Worry";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
        return true;
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
                    finish();
                });
    }

    private boolean isConnected(ForgetPassword forgetPassword) {
        ConnectivityManager connectivityManager = (ConnectivityManager) forgetPassword.getSystemService(CONNECTIVITY_SERVICE);

        NetworkCapabilities networkCapabilities = null;
        Network network = connectivityManager.getActiveNetwork();
        if (network != null) {
            networkCapabilities = connectivityManager.getNetworkCapabilities(network);
        }

        return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
    }
}