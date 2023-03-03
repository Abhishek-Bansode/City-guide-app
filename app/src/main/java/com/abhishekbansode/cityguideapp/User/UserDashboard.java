package com.abhishekbansode.cityguideapp.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.abhishekbansode.cityguideapp.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.abhishekbansode.cityguideapp.R;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        // Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);

        featuredRecycler();
    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.mcdonald_img,"Mcdonald's","this is demo description this is demo description this is demo description"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city1,"Edwin","this is demo description this is demo description this is demo description"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city2,"Sweet and Bakers","this is demo description this is demo description this is demo description"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);

        // making gradient code
        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

    }
}