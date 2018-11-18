package com.example.shazahassan.forecastapp.SqLite.model;

import android.provider.BaseColumns;

/**
 * Created by Shaza Hassan on 17-Nov-18.
 */
public class WeatherModel {

    public static final String TABLE_NAME = "Weather";
    public static final String CITY_COLUMN = "City";
    public static final String CURRENT_TEMP_COLUMN = "CurrentTemp";
    public static final String PRESSURE_COLUMN = "Pressure";
    public static final String HUMIDITY_COLUMN = "Humidity";
    public static final String SUNSET_COLUMN = "Sunset";
    public static final String DESCRIPTION_COLUMN = "Description";
    public static final String SUNRISE_COLUMN = "Sunrise";
    public static final String DATE_COLUMN = "Date";


    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CITY_COLUMN + " TEXT NOT NULL,"
            + CURRENT_TEMP_COLUMN + " INTEGER NOT NULL,"
            + PRESSURE_COLUMN + " INTEGER NOT NULL,"
            + HUMIDITY_COLUMN + " INTEGER NOT NULL,"
            + DESCRIPTION_COLUMN + " TEXT NOT NULL,"
            + SUNRISE_COLUMN + " TEXT NOT NULL,"
            + SUNSET_COLUMN + " TEXT NOT NULL,"
            + DATE_COLUMN + " INTEGER NOT NULL"
            + ")";

    private int temp, pressure, humidity, date;
    private String city, description, sunset, sunrise;

    public WeatherModel(int temp, int pressure, int humidity, int date, String city, String description, String sunset, String sunrise) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.date = date;
        this.city = city;
        this.description = description;
        this.sunset = sunset;
        this.sunrise = sunrise;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public WeatherModel() {
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }
}
