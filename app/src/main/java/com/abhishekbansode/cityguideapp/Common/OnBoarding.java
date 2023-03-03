package com.abhishekbansode.cityguideapp.Common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.abhishekbansode.cityguideapp.HelperClasses.SliderAdapter;
import com.abhishekbansode.cityguideapp.R;
import com.abhishekbansode.cityguideapp.User.UserDashboard;


public class OnBoarding extends AppCompatActivity {

    // variables
    ViewPager viewPager;
    LinearLayout dots_layout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button letsGetStarted;
    Animation animation;
    int currentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // to remove the status bar from screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        // Hooks
        viewPager = findViewById(R.id.slider);
        dots_layout = findViewById(R.id.dots);
        letsGetStarted = findViewById(R.id.get_started_btn);

        // Call adopters
        sliderAdapter = new SliderAdapter(this);

        // before this ViewPager2 was used, but doesn't work hence using ViewPager only
        viewPager.setAdapter(sliderAdapter);

        // Dots
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void skip(View view) {
        startActivity(new Intent(this, UserDashboard.class));
        finish();
    }

    public void next(View view) {
        viewPager.setCurrentItem(currentPosition + 1);
    }


    private void addDots(int position) {

        dots = new TextView[4];
        dots_layout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);

            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dots_layout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPosition = position;

            if (position == 0) {
                letsGetStarted.setVisibility(View.INVISIBLE);

            } else if (position == 1) {
                letsGetStarted.setVisibility(View.INVISIBLE);

            } else if (position == 2) {
                letsGetStarted.setVisibility(View.INVISIBLE);

            } else {
                // for using animation to letsGetStarted btn
                animation = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.bottom_anim);
                letsGetStarted.setAnimation(animation);
                letsGetStarted.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}