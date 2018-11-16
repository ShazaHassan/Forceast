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
//    public List<MainData> main = null;
//
    public class MainData {
        @SerializedName("temp")
        @Expose
        public double temp;

        @SerializedName("pressure")
        @Expose
        public String pressure;

        @SerializedName("humidity")
        @Expose
        public String humidity;
    }
//
//    public List<SysData> sysData = null;
//
//    public class SysData {
//
//        @SerializedName("country")
//        public String country;
//
//        @SerializedName("sunrise")
//        public String sunrise;
//
//        @SerializedName("sunset")
//        public String sunset;
//    }
}
