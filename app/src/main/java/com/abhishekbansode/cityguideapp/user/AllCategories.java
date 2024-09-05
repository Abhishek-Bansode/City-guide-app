package com.abhishekbansode.cityguideapp.user;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishekbansode.cityguideapp.R;

public class AllCategories extends AppCompatActivity {
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        // Hooks
        backBtn = findViewById(R.id.back_pressed);

        backBtn.setOnClickListener(view -> AllCategories.super.onBackPressed());
    }
}