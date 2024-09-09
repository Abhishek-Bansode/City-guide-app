package com.abhishekbansode.cityguideapp.User;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
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
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class UserHomeFragment extends Fragment {
    // Constants
    private static final float END_SCALE = 0.5f;

    // Variables
    LinearLayout contentView;
    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RelativeLayout searchBar;
    ImageView menuIcon, userLogInSignUpIcon;
    ChipNavigationBar chipNavigationBar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private OnBackPressedCallback backPressedCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        // Initialize view components
        initializeViews(view);

        // Setup RecyclerViews with adapters
        setupRecyclerViews();

        // Set up click listeners
        setupListeners();

        // Initialize and setup navigation drawer
        setupNavigationDrawer();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitDialog();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (backPressedCallback != null) {
            backPressedCallback.remove();
        }
    }

    /**
     * Initialize view components by hooking them to their respective IDs
     */
    private void initializeViews(View view) {
        featuredRecycler = view.findViewById(R.id.featured_recycler);
        mostViewedRecycler = view.findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = view.findViewById(R.id.categories_recycler1);
        menuIcon = view.findViewById(R.id.menu_icon);
        contentView = view.findViewById(R.id.content);
        userLogInSignUpIcon = view.findViewById(R.id.userLogInIcon);
        searchBar = view.findViewById(R.id.user_dashboard_searchbar);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.navigation_view);
        chipNavigationBar = view.findViewById(R.id.user_dashboard_bottom_nav_menu);
    }

    /**
     * Set up RecyclerViews with their respective adapters and layout managers
     */
    private void setupRecyclerViews() {
        setupRecyclerView(featuredRecycler, new FeaturedAdapter(loadFeaturedItems()), true);
        setupRecyclerView(mostViewedRecycler, new MostViewedAdapter(loadMostViewedItems()), true);
        setupRecyclerView(categoriesRecycler, new CategoriesAdapter(loadCategories()), true);
    }

    /**
     * Set up click listeners for UI elements
     */
    private void setupListeners() {
        userLogInSignUpIcon.setOnClickListener(v -> startActivity(new Intent(getActivity(), RetailerStartUpScreen.class)));

        searchBar.setOnClickListener(v -> {
            UserExploreFragment userExploreFragment = new UserExploreFragment();
            chipNavigationBar.setItemSelected(R.id.bottom_nav_explore, true);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.user_dashboard_fragment_container, userExploreFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    /**
     * Initialize and set up the navigation drawer with animations
     */
    private void setupNavigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(v -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    /**
     * Set up a RecyclerView with a specified adapter and layout orientation
     */
    private void setupRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter, boolean isHorizontal) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), isHorizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Load and return a list of featured items for the RecyclerView
     */
    private ArrayList<FeaturedHelperClass> loadFeaturedItems() {
        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.mcdonald_img, "McDonald's", "Demo description"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city1, "Edwin", "Demo description"));
        return featuredLocations;
    }

    /**
     * Load and return a list of most-viewed items for the RecyclerView
     */
    private ArrayList<MostViewedHelperClass> loadMostViewedItems() {
        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.mcdonald_img, "McDonald's", "Demo description"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.city2, "Tokyo", "Demo description"));
        return mostViewedLocations;
    }

    /**
     * Load and return a list of categories for the RecyclerView
     */
    private ArrayList<CategoriesHelperClass> loadCategories() {
        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(new GradientDrawable(), R.drawable.school_image, "Education"));
        categoriesHelperClasses.add(new CategoriesHelperClass(new GradientDrawable(), R.drawable.hospital_image, "HOSPITAL"));
        return categoriesHelperClasses;
    }

    /**
     * Show an exit dialog when back pressed
     */
    protected void showExitDialog() {
        AlertDialog.Builder exitDialog = new AlertDialog.Builder(requireContext());
        exitDialog.setTitle("Exit?");
        exitDialog.setMessage("Are you sure want to exit?");
        exitDialog.setIcon(R.drawable.exit_from_app);
        exitDialog.setPositiveButton("No", (dialogInterface, i) -> Toast.makeText(requireContext(), "Welcome back!", Toast.LENGTH_SHORT).show());
        exitDialog.setNegativeButton("Yes", (dialogInterface, i) -> requireActivity().finish());
        exitDialog.setNeutralButton("Cancel", (dialogInterface, i) -> Toast.makeText(requireContext(), "Operation Cancelled!", Toast.LENGTH_SHORT).show());
        exitDialog.show();
    }

    /**
     * Apply animations to the navigation drawer to create a sliding effect
     */
    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(ContextCompat.getColor(requireContext(), R.color.transparent));

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

    /**
     * Handle navigation item selections
     */
    public boolean onNavigationItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.nav_all_categories) {
            startActivity(new Intent(getContext(), AllCategories.class));
        }
        return true;
    }
}