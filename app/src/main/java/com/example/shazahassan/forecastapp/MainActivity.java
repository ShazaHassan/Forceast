package com.example.shazahassan.forecastapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.shazahassan.forecastapp.Adapter.AdapterForCities;
import com.example.shazahassan.forecastapp.forecast.Forecast;
import com.example.shazahassan.forecastapp.retrofit.APIClient;
import com.example.shazahassan.forecastapp.retrofit.APIWeatherInterface;
import com.example.shazahassan.forecastapp.weather.Weather;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.pagesForViewProduct);
        //adapter
        AdapterForCities pageAdapter = new AdapterForCities(this, getSupportFragmentManager());
        pager.setAdapter(pageAdapter);
        //tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabForViewProduct);
        tabLayout.setupWithViewPager(pager);

       // getReferences();

        //apiWeatherInterface = APIClient.client().create(APIWeatherInterface.class);

        //getWeather();
        //getForecast();

    }


}
