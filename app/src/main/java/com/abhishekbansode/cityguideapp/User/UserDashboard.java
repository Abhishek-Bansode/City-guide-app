package com.abhishekbansode.cityguideapp.User;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
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

public class UserDashboard extends AppCompatActivity {

    RecyclerView featuredRecycler,mostViewedRecycler, categoriesRecycler;

    private GradientDrawable gradient1, gradient2, gradient3, gradient4;

    RecyclerView.Adapter adapter;

    public UserDashboard() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        // Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler1);

        // calling this methods to create the views
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();
    }

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
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.mcdonald_img,"Mcdonald's","this is demo description this is demo description this is demo description"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city1,"Edwin","this is demo description this is demo description this is demo description"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city2,"Sweet and Bakers","this is demo description this is demo description this is demo description"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);
    }
}