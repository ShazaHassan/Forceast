package com.example.shazahassan.forecastapp.SqLite.model;

import android.provider.BaseColumns;

/**
 * Created by Shaza Hassan on 18-Nov-18.
 */
public class ForecastModel {

    public static final String TABLE_NAME = "ForecastTable";
    public static final String CITY_COLUMN = "City";
    public static final String DAY1_MIN = "Day1Min";
    public static final String DAY1_MAX = "Day1Max";
    public static final String DAY2 = "Day2";
    public static final String DAY2_MIN = "Day2Min";
    public static final String DAY2_MAX = "Day2Max";
    public static final String DAY3 = "Day3";
    public static final String DAY3_MIN = "Day3Min";
    public static final String DAY3_MAX = "Day3Max";
    public static final String DAY4 = "Day4";
    public static final String DAY4_MIN = "Day4Min";
    public static final String DAY4_MAX = "Day4Max";
    public static final String DAY5 = "Day5";
    public static final String DAY5_MIN = "Day5Min";
    public static final String DAY5_MAX = "Day5Max";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CITY_COLUMN + " TEXT UNIQUE NOT NULL,"
            + DAY1_MIN + " INTEGER NOT NULL,"
            + DAY1_MAX + " INTEGER NOT NULL,"
            + DAY2 + " INTEGER NOT NULL,"
            + DAY2_MIN + " INTEGER NOT NULL,"
            + DAY2_MAX + " INTEGER NOT NULL,"
            + DAY3 + " INTEGER NOT NULL,"
            + DAY3_MIN + " INTEGER NOT NULL,"
            + DAY3_MAX + " INTEGER NOT NULL,"
            + DAY4 + " INTEGER NOT NULL,"
            + DAY4_MIN + " INTEGER NOT NULL,"
            + DAY4_MAX + " INTEGER NOT NULL,"
            + DAY5 + " INTEGER NOT NULL,"
            + DAY5_MIN + " INTEGER NOT NULL,"
            + DAY5_MAX + " INTEGER NOT NULL"
            + ")";

    private String cityName;
    private int day1Min, day1Max, day2, day2Min, day2Max, day3, day3Min, day3Max, day4, day4Min, day4Max, day5, day5Min, day5Max;

    public ForecastModel() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getDay1Min() {
        return day1Min;
    }

    public void setDay1Min(int day1Min) {
        this.day1Min = day1Min;
    }

    public int getDay1Max() {
        return day1Max;
    }

    public void setDay1Max(int day1Max) {
        this.day1Max = day1Max;
    }

    public int getDay2() {
        return day2;
    }

    public void setDay2(int day2) {
        this.day2 = day2;
    }

    public int getDay2Min() {
        return day2Min;
    }

    public void setDay2Min(int day2Min) {
        this.day2Min = day2Min;
    }

    public int getDay2Max() {
        return day2Max;
    }

    public void setDay2Max(int day2Max) {
        this.day2Max = day2Max;
    }

    public int getDay3() {
        return day3;
    }

    public void setDay3(int day3) {
        this.day3 = day3;
    }

    public int getDay3Min() {
        return day3Min;
    }

    public void setDay3Min(int day3Min) {
        this.day3Min = day3Min;
    }

    public int getDay3Max() {
        return day3Max;
    }

    public void setDay3Max(int day3Max) {
        this.day3Max = day3Max;
    }

    public int getDay4() {
        return day4;
    }

    public void setDay4(int day4) {
        this.day4 = day4;
    }

    public int getDay4Min() {
        return day4Min;
    }

    public void setDay4Min(int day4Min) {
        this.day4Min = day4Min;
    }

    public int getDay4Max() {
        return day4Max;
    }

    public void setDay4Max(int day4Max) {
        this.day4Max = day4Max;
    }

    public int getDay5() {
        return day5;
    }

    public void setDay5(int day5) {
        this.day5 = day5;
    }

    public int getDay5Min() {
        return day5Min;
    }

    public void setDay5Min(int day5Min) {
        this.day5Min = day5Min;
    }

    public int getDay5Max() {
        return day5Max;
    }

    public void setDay5Max(int day5Max) {
        this.day5Max = day5Max;
    }
}
