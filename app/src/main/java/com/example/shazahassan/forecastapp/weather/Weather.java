package com.example.shazahassan.forecastapp.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shaza Hassan on 16-Nov-18.
 */
public class Weather {

    @SerializedName("dt")
    public Integer dt;

    @SerializedName("name")
    public String name;
    @SerializedName("weather")
    public List<WeatherData> weather = null;

    public class WeatherData {
        @SerializedName("description")
        public String description;
    }
//
    @SerializedName("main")
    @Expose
    public MainData main;
    @SerializedName("sys")
    @Expose
    public SysData sysData = null;

//    public List<MainData> main = null;
//
    public class MainData {
        @SerializedName("temp")
        @Expose
        public float temp;

        @SerializedName("pressure")
        @Expose
        public float pressure;

        @SerializedName("humidity")
        @Expose
        public float humidity;
    }

    public class SysData {

        @SerializedName("country")
        @Expose
        public String country;

        @SerializedName("sunrise")
        @Expose
        public long sunrise;

        @SerializedName("sunset")
        @Expose
        public long sunset;
    }
}
