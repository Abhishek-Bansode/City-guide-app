package com.abhishekbansode.cityguideapp.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.abhishekbansode.cityguideapp.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class UserExploreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_explore, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Ensure that bottom navigation updates to reflect 'Explore' being selected
        if (getActivity() != null) {
            ChipNavigationBar chipNavigationBar = getActivity().findViewById(R.id.user_dashboard_bottom_nav_menu);
            if (chipNavigationBar != null) {
                chipNavigationBar.setItemSelected(R.id.bottom_nav_explore, true);
            }
        }
    }
}
