package com.example.shazahassan.forecastapp.retrofit;

import com.example.shazahassan.forecastapp.forecast.Forecast;
import com.example.shazahassan.forecastapp.weather.Weather;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Shaza Hassan on 16-Nov-18.
 */
public interface APIWeatherInterface {
    @GET("weather?q=Cairo&units=metric&appid=847d244e3b42a0b0f7f0e01c9ddd2ce6")
    Call<Weather> cairoWeather();

    @GET("forecast?q=cairo&units=metric&appid=847d244e3b42a0b0f7f0e01c9ddd2ce6")
    Call<Forecast> cairoForecast();
}
