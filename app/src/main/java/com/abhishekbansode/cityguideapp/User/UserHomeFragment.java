package com.abhishekbansode.cityguideapp.User;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.MostViewedAdapter;
import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.abhishekbansode.cityguideapp.R;

import java.util.ArrayList;

public class UserHomeFragment extends Fragment {

    // Variables
    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        // Hook the RecyclerViews
        featuredRecycler = view.findViewById(R.id.featured_recycler);
        mostViewedRecycler = view.findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = view.findViewById(R.id.categories_recycler1);

        // Call methods to set up RecyclerViews
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();

        return view;
    }

    // Recycler View Functions
    private void categoriesRecycler() {

        // Example code for categories
        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();

        categoriesHelperClasses.add(new CategoriesHelperClass(new GradientDrawable(), R.drawable.school_image, "Education"));
        categoriesHelperClasses.add(new CategoriesHelperClass(new GradientDrawable(), R.drawable.hospital_image, "HOSPITAL"));
        categoriesHelperClasses.add(new CategoriesHelperClass(new GradientDrawable(), R.drawable.restaurant_image, "Restaurant"));

        categoriesRecycler.setHasFixedSize(true);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setAdapter(adapter);
    }

    private void mostViewedRecycler() {

        // Example code for most viewed items
        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();

        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.mcdonald_img, "McDonald's", "Demo description"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.city2, "Tokyo", "Demo description"));

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new MostViewedAdapter(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);
    }

    private void featuredRecycler() {

        // Example code for featured items
        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.mcdonald_img, "McDonald's", "Demo description"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city1, "Edwin", "Demo description"));

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);
    }
}