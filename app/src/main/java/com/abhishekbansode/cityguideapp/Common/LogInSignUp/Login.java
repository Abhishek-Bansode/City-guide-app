package com.abhishekbansode.cityguideapp.Common.LogInSignUp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.Databases.SessionManager;
import com.abhishekbansode.cityguideapp.LocationOwner.RetailerDashboard;
import com.abhishekbansode.cityguideapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Objects;

public class Login extends AppCompatActivity {

    // variables
    Button logInBtn, forgetPasswordBtn, createAccountBtn;
    ImageView backBtn;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    ProgressBar progressbar;
    CheckBox rememberMe;
    TextInputEditText phoneNumberEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_login);

        // hooks
        backBtn = findViewById(R.id.login_back_button);
        logInBtn = findViewById(R.id.letTheUserLogin);
        createAccountBtn = findViewById(R.id.create_acc_btn);
        forgetPasswordBtn = findViewById(R.id.login_forget_password);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);
        progressbar = findViewById(R.id.login_progress_bar);
        rememberMe = findViewById(R.id.remember_me);
        phoneNumberEditText = findViewById(R.id.login_phone_number_editText);
        passwordEditText = findViewById(R.id.login_password_editText);

        // Check whether Phone-Number and Password is already saved in Shared Preferences or Not
        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()) {
            HashMap<String, String> rememberMeDetails = sessionManager.getRememberMeDetailsFromSession();

            // Set the values for phoneNumber and password
            phoneNumberEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSION_PHONENUMBER));
            passwordEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSION_PASSWORD));
        }


        // On-Click listeners
        logInBtn.setOnClickListener(view -> {
            if (!isConnected1((Login) getApplicationContext())) {
                showCustomDialog();
            }

            if (!validateFields()) {
                return;
            }

            // progress bar
            progressbar.setVisibility(View.VISIBLE);

            // get values from fields
            String _phoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
            String _password = Objects.requireNonNull(password.getEditText()).getText().toString().trim();

            // remove '0' at the start if entered by the User
            if (_phoneNumber.charAt(0) == '0') {
                _phoneNumber = _phoneNumber.substring(1);
            }
            String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;

            if (rememberMe.isChecked()) {
                // As this session is already defined in this scope
                sessionManager.createRememberMeSession(_phoneNumber, _password);
            }

            // Check whether User exists or not in Firebase Database
            Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        phoneNumber.setError(null);
                        phoneNumber.setErrorEnabled(false);

                        String systemPassword = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                        assert systemPassword != null;
                        if (systemPassword.equals(_password)) {
                            password.setError(null);
                            password.setErrorEnabled(false);

                            // Get User data from Firebase Database
                            String _fullname = snapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                            String _username = snapshot.child(_completePhoneNumber).child("username").getValue(String.class);
                            String _email = snapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                            String _phoneNo = snapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                            String _password = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                            String _dataOfBirth = snapshot.child(_completePhoneNumber).child("date").getValue(String.class);
                            String _gender = snapshot.child(_completePhoneNumber).child("gender").getValue(String.class);

                            // Create Session
                            SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_USER_SESSION);
                            sessionManager.createLoginSession(_fullname, _username, _email, _phoneNo, _password, _dataOfBirth, _gender);

                            startActivity(new Intent(getApplicationContext(), RetailerDashboard.class));

                        } else {
                            progressbar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Password does not exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "No such user exists", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // function to call the forget password screen
        forgetPasswordBtn.setOnClickListener(view ->  {
            Intent intent = new Intent(this, ForgetPassword.class);
            startActivity(intent);
        });

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, RetailerStartUpScreen.class);
            startActivity(intent);
        });

        createAccountBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
        });
    }

    // check the device is connected to internet or not
    private boolean isConnected1(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(CONNECTIVITY_SERVICE);

        NetworkCapabilities networkCapabilities = null;
        Network network = connectivityManager.getActiveNetwork();
        if (network != null) {
            networkCapabilities = connectivityManager.getNetworkCapabilities(network);
        }

        return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
    }

    // check the internet connection
    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
                    finish();
                });
    }

    // fields validations
    private boolean validateFields() {
        String _phoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        String _password = Objects.requireNonNull(password.getEditText()).getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Phone number can not be empty");
            phoneNumber.requestFocus();
            return false;
        } else if (_password.isEmpty()) {
            password.setError("Password can not be empty");
            password.requestFocus();
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
}