package com.abhishekbansode.cityguideapp.Common.LogInSignUp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    PinView pinFromUser;
    String codeBySystem;
    TextView otpDescriptionText;
    String fullname, phoneNo, email, username, password, date, gender, whatToDo;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_otp);

        // Hooks
        pinFromUser = findViewById(R.id.pin_view);
        otpDescriptionText = findViewById(R.id.otp_description_text);

        // Initialize the FireBase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get all data from intent
        fullname = getIntent().getStringExtra("fullName");
        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        date = getIntent().getStringExtra("date");
        gender = getIntent().getStringExtra("gender");
        phoneNo = getIntent().getStringExtra("phoneNo");
        whatToDo = getIntent().getStringExtra("whatToDo");

       otpDescriptionText.setText("Enter OTP Sent On: " + phoneNo);
       sendVerificationCodeToUser(phoneNo);
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null) {
                pinFromUser.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeBySystem = s;
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInUsingCredential(credential);
    }

//    private void signInUsingCredential(PhoneAuthCredential credential) {
//
//    }
    private void signInUsingCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(VerifyOTP.this, "Verification Completed.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    // first check call and then redirect user accordingly to the Profile or to set new password screen
    public void callNextScreenFromOTP(View view) {
        String code = pinFromUser.getText().toString();
        if(!code.isEmpty()) {
            verifyCode(code);
        }
    }

    public void goToHomeOTP(View view) {

    }
}