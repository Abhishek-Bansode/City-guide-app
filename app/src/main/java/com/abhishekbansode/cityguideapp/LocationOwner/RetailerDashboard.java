package com.abhishekbansode.cityguideapp.LocationOwner;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abhishekbansode.cityguideapp.Databases.SessionManager;
import com.abhishekbansode.cityguideapp.R;

import java.util.HashMap;

public class RetailerDashboard extends AppCompatActivity {

    // Variables
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_retailer_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Hooks
        textView = findViewById(R.id.textView);

        SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_USER_SESSION);
        HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();

        String fullName = userDetails.get(SessionManager.KEY_FULLNAME);
        String phoneNumber = userDetails.get(SessionManager.KEY_PHONENUMBER);

        textView.setText(fullName + "\n" + phoneNumber);
    }
}