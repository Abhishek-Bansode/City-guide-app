package com.abhishekbansode.cityguideapp.User;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.abhishekbansode.cityguideapp.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class UserDashboard extends AppCompatActivity {

    // Variables
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_dashboard_fragment_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Hooks
        chipNavigationBar = findViewById(R.id.user_dashboard_bottom_nav_menu);

        // Bottom-nav functions
        chipNavigationBar.setItemSelected(R.id.bottom_nav_home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.user_dashboard_fragment_container, new UserHomeFragment()).commit();
        bottomMenu();

    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(i -> {
            Fragment fragment;

            if (i == (R.id.bottom_nav_explore)) {
                fragment = new UserExploreFragment();
            } else if (i == (R.id.bottom_nav_wishlist)) {
                fragment = new UserWishlistFragment();
            } else if (i == (R.id.bottom_nav_profile)) {
                fragment = new UserProfileFragment();
            } else {
                fragment = new UserHomeFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.user_dashboard_fragment_container, fragment).commit();
        });
    }
}