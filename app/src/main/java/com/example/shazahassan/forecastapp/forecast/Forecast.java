package com.example.shazahassan.forecastapp.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shaza Hassan on 16-Nov-18.
 */
public class Forecast {
    @SerializedName("list")
    public List<listData> list = null;

    public class listData{
        @SerializedName("dt")
        public long dt;
        @SerializedName("main")
        @Expose
        public mainData main;
    }

    public class mainData{
        @SerializedName("temp")
        @Expose
        public double temp;
    }
}
