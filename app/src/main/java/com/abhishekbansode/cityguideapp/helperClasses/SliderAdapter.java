package com.abhishekbansode.cityguideapp.helperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.abhishekbansode.cityguideapp.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    java.lang.Object Object;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    // declarations for these arrays which hold image, title and descriptions
    int[] images;
    int[] titles;
    int[] description;

    {
        images = new int[]{
                // using some other illustrations
//                R.drawable.search_place,
//                R.drawable.make_a_call,
//                R.drawable.add_missing_place,
//                R.drawable.sit_back_and_relax

                // just need to horizontal flip in this png
//                R.drawable.boy_with_laptop_and_cup,
                R.drawable.boy_with_laptop_and_cup,
                R.drawable.man_using_laptop_and_talking_on_the_phone,

                R.drawable.gir_with_laptop_and_coffee_sitting_on_floor,
                R.drawable.young_man_and_woman_sitting_on_bench

        };
        titles = new int[]{
                R.string.first_slide_title,
                R.string.second_slide_title,
                R.string.third_slide_title,
                R.string.fourth_slide_title
        };
        description = new int[]{
                R.string.first_slide_desc,
                R.string.second_slide_desc,
                R.string.third_slide_desc,
                R.string.fourth_slide_desc

        };
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view == Object;
//         above line is not working, only below line is working
        return view == object;
    }

    @NonNull
    @Override
    public java.lang.Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout,container,false);

        // Hooks
        ImageView imageView = view.findViewById(R.id.slider_image);
        TextView heading = view.findViewById(R.id.slider_heading);
        TextView description = view.findViewById(R.id.slider_desc);

        imageView.setImageResource(images[position]);
        heading.setText(titles[position]);
        description.setText(this.description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull java.lang.Object object) {
        container.removeView((ConstraintLayout) Object);
//        container.removeView((ConstraintLayout) object);
    }
}
