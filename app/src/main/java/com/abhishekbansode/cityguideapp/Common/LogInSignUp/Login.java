package com.abhishekbansode.cityguideapp.Common.LogInSignUp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

public class Login extends AppCompatActivity {

    // variables
    Button logInBtn;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_login);

        // hooks
        logInBtn = findViewById(R.id.letTheUserLogin);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);
        progressbar = findViewById(R.id.login_progress_bar);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isConnected((Login) getApplicationContext())) {
                    showCustomDialog();
                }

                if (!validateFields()) {
                    return;
                }

                // progress bar
                progressbar.setVisibility(View.VISIBLE);

                // get data
                String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
                String _password = password.getEditText().getText().toString().trim();

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
                            if (systemPassword.equals(_password)) {
                                password.setError(null);
                                password.setErrorEnabled(false);

                                // data showing
                                String _fullName = snapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                                String _email = snapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                                String _phoneNo = snapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                                String _dataOfBirth = snapshot.child(_completePhoneNumber).child("date").getValue(String.class);

                                Toast.makeText(Login.this, _fullName + "\n" + _email + "\n" + _phoneNo + "\n" + _dataOfBirth, Toast.LENGTH_SHORT).show();

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
            }
        });

    }


    // check the internet connection
    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
                        finish();
                    }
                });
    }


    public void forgetPasswordScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
    }

    private boolean validateFields() {
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

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

        }
        return true;
    }
}