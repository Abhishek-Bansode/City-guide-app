package com.abhishekbansode.cityguideapp.common.logInSignUp;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class Login extends AppCompatActivity {

    // variables
    Button logInBtn, forgetPasswordBtn, createAccountBtn;
    ImageView backBtn;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    ProgressBar progressbar;

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

            // get data
            String _phoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
            String _password = Objects.requireNonNull(password.getEditText()).getText().toString().trim();

            if (_phoneNumber.charAt(0) == '0') {
                _phoneNumber = _phoneNumber.substring(1);
            }
            String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;

            // Database
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

                            // data showing
                            String _fullName = snapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                            String _email = snapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                            String _phoneNo = snapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                            String _dataOfBirth = snapshot.child(_completePhoneNumber).child("date").getValue(String.class);

                            Toast.makeText(Login.this, _fullName + "\n" + _email + "\n" + _phoneNo + "\n" + _dataOfBirth, Toast.LENGTH_LONG).show();

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