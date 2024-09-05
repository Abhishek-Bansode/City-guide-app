package com.abhishekbansode.cityguideapp.helperClasses.HomeAdapter;


public class MostViewedHelperClass {
    int image;

    public MostViewedHelperClass(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public int getImageView() {
        return image;
    }

    public String getTextView() {
        return title;
    }

    String title, description;

}
