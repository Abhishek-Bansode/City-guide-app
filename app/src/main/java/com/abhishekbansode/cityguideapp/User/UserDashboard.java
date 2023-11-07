package com.abhishekbansode.cityguideapp.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishekbansode.cityguideapp.Common.LogInSignUp.RetailerStartUpScreen;
import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.MostViewedAdapter;
import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.abhishekbansode.cityguideapp.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Variables
    LinearLayout contentView;
    static final float END_SCALE = 0.5f;
    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    ImageView menuIcon, userLogInSignUpIcon;

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    RecyclerView.Adapter adapter;

    public UserDashboard() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        // Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler1);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        userLogInSignUpIcon = findViewById(R.id.userLogInIcon);


        // Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationDrawer();

        // Recycler View Function calls
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();


        // ON Click -> CallRetailerScreen
        userLogInSignUpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
            }
        });

        // backPressed on main activity
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // creating alert
                AlertDialog.Builder exitDialog = new AlertDialog.Builder(UserDashboard.this);

                exitDialog.setTitle("Exit?");
                exitDialog.setMessage("Are you sure want to exit?");
                exitDialog.setIcon(R.drawable.exit_from_app);

                exitDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(UserDashboard.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                    }
                });

                exitDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Back is pressed... Finishing the activity
                        finish();
                    }
                });
                
                exitDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(UserDashboard.this, "Operation Cancelled!", Toast.LENGTH_SHORT).show();
                    }
                });
                exitDialog.show();
            }
        });
    }

    // Navigation Drawer Functions
    private void navigationDrawer() {

        // Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {

        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset

                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;

                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else
//            super.onBackPressed();
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_all_categories:
                startActivity(new Intent(getApplicationContext(),AllCategories.class));
                break;
        }


        return true;
    }

    // Recycler View Functions
    private void categoriesRecycler() {

        // All Gradients for this view are

        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});


        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();

        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.school_image, "Education"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient2, R.drawable.hospital_image, "HOSPITAL"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient3, R.drawable.restaurant_image, "Restaurant"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient4, R.drawable.shopping_image, "Shopping"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.transport_image, "Transport"));

        categoriesRecycler.setHasFixedSize(true);

        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);
    }

    private void mostViewedRecycler() {

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();

        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.mcdonald_img, "McDonald's", "dhhsadbkdkadgagd"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.city2, "Edenrobe", "dgdjdgdvmffhfjkfbfg"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.restaurant_image, "J.", "hfhffftdbdhfufkvmfhfbdhd"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.mcdonald_img, "Walmart", "dhdhdgdtfnfmvkjvhgsf"));

        adapter = new MostViewedAdapter(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);
    }

    private void featuredRecycler() {

        // All Gradients for this view are
//        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.mcdonald_img, "Mcdonald's", "this is demo description this is demo description this is demo description"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city1, "Edwin", "this is demo description this is demo description this is demo description"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city2, "Sweet and Bakers", "this is demo description this is demo description this is demo description"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);
    }


    // Normal functions
    public void CallRetailerScreen(View view) {
        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
    }

}