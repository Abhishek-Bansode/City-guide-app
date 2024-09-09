package com.abhishekbansode.cityguideapp.User;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.abhishekbansode.cityguideapp.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class UserDashboard extends AppCompatActivity {

    // Variables
    private ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullscreenMode();
        setContentView(R.layout.activity_user_dashboard);
        applyWindowInsets();

        // Initialize bottom navigation bar
        chipNavigationBar = findViewById(R.id.user_dashboard_bottom_nav_menu);

        // Set up initial fragment and bottom navigation
        initializeFragment();
        setupBottomNavigation();

        // Handle back press behavior
        handleBackPress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.user_dashboard_fragment_container);

        if (currentFragment instanceof UserHomeFragment) {
            chipNavigationBar.setItemSelected(R.id.bottom_nav_home, true);
        } else if (currentFragment instanceof UserExploreFragment) {
            chipNavigationBar.setItemSelected(R.id.bottom_nav_explore, true);
        } else if (currentFragment instanceof UserWishlistFragment) {
            chipNavigationBar.setItemSelected(R.id.bottom_nav_wishlist, true);
        } else if (currentFragment instanceof UserProfileFragment) {
            chipNavigationBar.setItemSelected(R.id.bottom_nav_profile, true);
        }
    }

    /**
     * Set the activity to fullscreen mode.
     */
    private void setFullscreenMode() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * Apply window insets to ensure proper padding for system bars.
     */
    private void applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_dashboard_fragment_container), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Initialize the fragment with UserHomeFragment and set the bottom navigation item.
     */
    private void initializeFragment() {
        chipNavigationBar.setItemSelected(R.id.bottom_nav_home, true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.user_dashboard_fragment_container, new UserHomeFragment())
                .commit();
    }

    /**
     * Set up the bottom navigation menu to switch between fragments.
     */
    private void setupBottomNavigation() {
        chipNavigationBar.setOnItemSelectedListener(itemId -> {
            Fragment fragment;

            if (itemId == R.id.bottom_nav_explore) {
                fragment = new UserExploreFragment();
            } else if (itemId == R.id.bottom_nav_wishlist) {
                fragment = new UserWishlistFragment();
            } else if (itemId == R.id.bottom_nav_profile) {
                fragment = new UserProfileFragment();
            } else {
                fragment = new UserHomeFragment();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.user_dashboard_fragment_container, fragment)
                    .commit();
        });
    }

    /**
     * Handle the back press action to either navigate to UserHomeFragment or show exit dialog.
     */
    private void handleBackPress() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.user_dashboard_fragment_container);

                if (currentFragment instanceof UserHomeFragment) {
                    // If the current fragment is UserHomeFragment, show the exit dialog
                    ((UserHomeFragment) currentFragment).showExitDialog();
                } else {
                    // If it's not UserHomeFragment, navigate to UserHomeFragment
                    Fragment userHomeFragment = getSupportFragmentManager().findFragmentByTag("UserHomeFragment");

                    if (userHomeFragment == null || !userHomeFragment.isAdded()) {
                        // If UserHomeFragment is not added yet, add it and update the bottom navigation
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.user_dashboard_fragment_container, new UserHomeFragment(), "UserHomeFragment")
                                .addToBackStack(null)
                                .commit();

                        // Set bottom navigation to 'Home' item
                        chipNavigationBar.setItemSelected(R.id.bottom_nav_home, true);
                    } else {
                        // If UserHomeFragment is already added, pop the back stack to navigate to it
                        getSupportFragmentManager().popBackStack();

                        // Set bottom navigation to 'Home' item
                        chipNavigationBar.setItemSelected(R.id.bottom_nav_home, true);
                    }
                }
            }
        });
    }
}
