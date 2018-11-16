package com.example.shazahassan.forecastapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shazahassan.forecastapp.R;

/**
 * Created by Shaza Hassan on 17-Nov-18.
 */
public class NewYorkFragment extends Fragment {
    View rootView;
    public NewYorkFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.weather_template, container, false);
        return rootView;
    }
}
