package com.abhishekbansode.cityguideapp.common.logInSignUp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SetNewPassword extends AppCompatActivity {

    // Variables
    TextInputLayout newPassword, confirmNewPassword;
    Button updateBtn;
    ImageView backBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        // Hooks
        newPassword = findViewById(R.id.new_password);
        confirmNewPassword = findViewById(R.id.confirm_new_password);
        updateBtn = findViewById(R.id.update_credentials);
        backBtn = findViewById(R.id.new_credential_backBtn);
        progressBar = findViewById(R.id.new_credential_progress_bar);

        // Update Users Password on UPDATE button
        updateBtn.setOnClickListener(view -> {
            // Check the internet connection
            if (!isConnected((SetNewPassword) getApplicationContext())) {
                showCustomDialog();
                return;
            }

            // validate the Phone Number
            if (!validateNewPassword() | !validateConfirmPassword()) {
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            // Get data from fields
            String _newPassword = Objects.requireNonNull(newPassword.getEditText()).toString().trim();
            String _phoneNumber = Objects.requireNonNull(getIntent().getStringExtra("phoneNo"));

            // Update Data in the Firebase and in Sessions
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(_phoneNumber).child("password").setValue(_newPassword);

            startActivity(new Intent(getApplicationContext(), ForgetPasswordSuccessMessage.class));
            finish();
        });

        // Navigate the user to the StartUpScreen
        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
            finish();
        });
    }

    private boolean validateConfirmPassword() {
        String _newPassword = Objects.requireNonNull(newPassword.getEditText()).toString().trim();
        String _confirmPassword = Objects.requireNonNull(confirmNewPassword.getEditText()).toString().trim();

        // Check if confirm password is empty
        if (_confirmPassword.isEmpty()) {
            confirmNewPassword.setError("Please confirm your password");
            return false;
        }

        // Check if the passwords match
        if (!_newPassword.equals(_confirmPassword)) {
            confirmNewPassword.setError("Passwords do not match");
            return false;
        }

        // If passwords match
        return true;
    }

    private boolean validateNewPassword() {
        String _newPassword = Objects.requireNonNull(newPassword.getEditText()).toString().trim();

        // Check if password is empty
        if (_newPassword.isEmpty()) {
            newPassword.setError("Password cannot be empty");
            return false;
        }

        // Check password length (e.g., minimum 8 characters)
        if (_newPassword.length() < 8) {
            newPassword.setError("Password must be at least 8 characters");
            return false;
        }

        // Check for at least one uppercase letter
        if (!_newPassword.matches(".*[A-Z].*")) {
            newPassword.setError("Password must contain at least one uppercase letter");
            return false;
        }

        // Check for at least one digit
        if (!_newPassword.matches(".*\\d.*")) {
            newPassword.setError("Password must contain at least one number");
            return false;
        }

        // Check for at least one special character
        if (!_newPassword.matches(".*[!@#$%^&*+=?_-].*")) {
            newPassword.setError("Password must contain at least one special character");
            return false;
        }

        // If all conditions are met
        return true;
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetNewPassword.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
                    finish();
                });
    }

    // check the device is connected to internet or not
    private boolean isConnected(SetNewPassword setNewPassword) {
        ConnectivityManager connectivityManager = (ConnectivityManager) setNewPassword.getSystemService(CONNECTIVITY_SERVICE);

        NetworkCapabilities networkCapabilities = null;
        Network network = connectivityManager.getActiveNetwork();
        if (network != null) {
            networkCapabilities = connectivityManager.getNetworkCapabilities(network);
        }

        return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
    }
}