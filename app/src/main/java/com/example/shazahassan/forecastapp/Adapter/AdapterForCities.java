package com.example.shazahassan.forecastapp.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shazahassan.forecastapp.Fragment.CairoFragment;
import com.example.shazahassan.forecastapp.Fragment.IstanbulFragment;
import com.example.shazahassan.forecastapp.Fragment.LondonFragment;
import com.example.shazahassan.forecastapp.Fragment.NewYorkFragment;
import com.example.shazahassan.forecastapp.Fragment.ParisFragment;

/**
 * Created by Shaza Hassan on 17-Nov-18.
 */
public class AdapterForCities extends FragmentPagerAdapter {
    private Context context;

    public AdapterForCities(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new CairoFragment();
        } else if (position == 1) {
            return new LondonFragment();
        } else if (position == 2) {
            return new NewYorkFragment();
        } else if (position == 3) {
            return new ParisFragment();
        } else {
            return new IstanbulFragment();
        }

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Cairo";
        } else if (position == 1) {
            return "London";
        } else if (position == 2) {
            return "New York";
        } else if (position == 3) {
            return "Paris";
        } else {
            return "Istanbul";
        }
    }
}
