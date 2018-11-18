package com.example.shazahassan.forecastapp.SqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.shazahassan.forecastapp.SqLite.model.ForecastModel;
import com.example.shazahassan.forecastapp.SqLite.model.WeatherModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaza Hassan on 17-Nov-18.
 */
public class WeatherDatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    boolean Added = false, addedForecast = false;

    // Database Name
    private static final String DATABASE_NAME = "forecast.db";

    private Context context;

    public WeatherDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(WeatherModel.CREATE_TABLE);
        sqLiteDatabase.execSQL(ForecastModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherModel.TABLE_NAME);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void insertNewData(WeatherModel weatherModel) {
        Added = false;
        SQLiteDatabase database = this.getWritableDatabase();
        List<WeatherModel> models = getWeatherForAllCities();
        if (models.size() != 0) {
            for (WeatherModel model : models) {
                if (model.getCity().equals(weatherModel.getCity())) {
                    update(model, model.getCity());
                    Added = true;
                    break;
                }
            }
        } else {
            save(weatherModel, database);
        }
        if (!Added) {
            save(weatherModel, database);
        }

        database.close();
    }

    private void save(WeatherModel weatherModel, SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(WeatherModel.CITY_COLUMN, weatherModel.getCity());
        values.put(WeatherModel.DESCRIPTION_COLUMN, weatherModel.getDescription());
        values.put(WeatherModel.CURRENT_TEMP_COLUMN, weatherModel.getTemp());
        values.put(WeatherModel.SUNRISE_COLUMN, weatherModel.getSunrise());
        values.put(WeatherModel.SUNSET_COLUMN, weatherModel.getSunset());
        values.put(WeatherModel.PRESSURE_COLUMN, weatherModel.getPressure());
        values.put(WeatherModel.HUMIDITY_COLUMN, weatherModel.getHumidity());
        values.put(WeatherModel.DATE_COLUMN, weatherModel.getDate());
        database.insert(WeatherModel.TABLE_NAME, null, values);
    }

    public WeatherModel getOneCity(String cityName) {
        WeatherModel model = new WeatherModel();
        String selectQuery = "SELECT  * FROM " + WeatherModel.TABLE_NAME + " WHERE " + WeatherModel.CITY_COLUMN + " =? ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cityName});
        if (cursor.moveToNext()) {
            model.setCity(cursor.getString(cursor.getColumnIndex(WeatherModel.CITY_COLUMN)));
            Log.v("cityName", cursor.getString(cursor.getColumnIndex(WeatherModel.CITY_COLUMN)));
            model.setDescription(cursor.getString(cursor.getColumnIndex(WeatherModel.DESCRIPTION_COLUMN)));
            model.setTemp(cursor.getInt(cursor.getColumnIndex(WeatherModel.CURRENT_TEMP_COLUMN)));
            model.setHumidity(cursor.getInt(cursor.getColumnIndex(WeatherModel.HUMIDITY_COLUMN)));
            model.setPressure(cursor.getInt(cursor.getColumnIndex(WeatherModel.PRESSURE_COLUMN)));
            model.setSunrise(cursor.getString(cursor.getColumnIndex(WeatherModel.SUNRISE_COLUMN)));
            model.setSunset(cursor.getString(cursor.getColumnIndex(WeatherModel.SUNSET_COLUMN)));
            model.setDate(cursor.getInt(cursor.getColumnIndex(WeatherModel.DATE_COLUMN)));
            Log.v("dateFromDB", cursor.getInt(cursor.getColumnIndex(WeatherModel.DATE_COLUMN)) + "");
        }
        return model;
    }

    public List<WeatherModel> getWeatherForAllCities() {

        List<WeatherModel> models = new ArrayList<>();
        WeatherModel model = new WeatherModel();
        String selectQuery = "SELECT  * FROM " + WeatherModel.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            model.setCity(cursor.getString(cursor.getColumnIndex(WeatherModel.CITY_COLUMN)));
            Log.v("cityName", cursor.getString(cursor.getColumnIndex(WeatherModel.CITY_COLUMN)));
            model.setDescription(cursor.getString(cursor.getColumnIndex(WeatherModel.DESCRIPTION_COLUMN)));
            model.setTemp(cursor.getInt(cursor.getColumnIndex(WeatherModel.CURRENT_TEMP_COLUMN)));
            model.setHumidity(cursor.getInt(cursor.getColumnIndex(WeatherModel.HUMIDITY_COLUMN)));
            model.setPressure(cursor.getInt(cursor.getColumnIndex(WeatherModel.PRESSURE_COLUMN)));
            model.setSunrise(cursor.getString(cursor.getColumnIndex(WeatherModel.SUNRISE_COLUMN)));
            model.setSunset(cursor.getString(cursor.getColumnIndex(WeatherModel.SUNSET_COLUMN)));
            models.add(model);
        }
        return models;
    }

    public void update(WeatherModel weatherModel, String cityName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WeatherModel.DESCRIPTION_COLUMN, weatherModel.getDescription());
        values.put(WeatherModel.CURRENT_TEMP_COLUMN, weatherModel.getTemp());
        values.put(WeatherModel.SUNRISE_COLUMN, weatherModel.getSunrise());
        values.put(WeatherModel.SUNSET_COLUMN, weatherModel.getSunset());
        values.put(WeatherModel.PRESSURE_COLUMN, weatherModel.getPressure());
        values.put(WeatherModel.HUMIDITY_COLUMN, weatherModel.getHumidity());
        values.put(WeatherModel.DATE_COLUMN, weatherModel.getDate());
        db.update(WeatherModel.TABLE_NAME, values, WeatherModel.CITY_COLUMN + " = ?", new String[]{cityName});
    }

    public void insertNewDataForecast(ForecastModel forecastModel) {
        addedForecast = false;
        SQLiteDatabase database = this.getWritableDatabase();
        List<ForecastModel> models = getForecastForAllCities();
        if (models.size() != 0) {
            for (ForecastModel model : models) {
                if (model.getCityName().equals(forecastModel.getCityName())) {
                    updateForecast(model, model.getCityName());
                    addedForecast = true;
                    break;
                }
            }

        } else {
            saveForecast(forecastModel, database);
        }
        if (!addedForecast) {
            saveForecast(forecastModel, database);
        }

        database.close();
    }

    private void saveForecast(ForecastModel forecastModel, SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(ForecastModel.CITY_COLUMN, forecastModel.getCityName());
        values.put(ForecastModel.DAY1_MIN, forecastModel.getDay1Min());
        values.put(ForecastModel.DAY1_MAX, forecastModel.getDay1Max());

        values.put(ForecastModel.DAY2, forecastModel.getDay2());
        values.put(ForecastModel.DAY2_MIN, forecastModel.getDay2Min());
        values.put(forecastModel.DAY2_MAX, forecastModel.getDay2Max());

        values.put(forecastModel.DAY3, forecastModel.getDay3());
        values.put(ForecastModel.DAY3_MIN, forecastModel.getDay3Min());
        values.put(forecastModel.DAY3_MAX, forecastModel.getDay3Max());

        values.put(forecastModel.DAY4, forecastModel.getDay4());
        values.put(ForecastModel.DAY4_MIN, forecastModel.getDay4Min());
        values.put(forecastModel.DAY4_MAX, forecastModel.getDay4Max());

        values.put(forecastModel.DAY5, forecastModel.getDay5());
        values.put(ForecastModel.DAY5_MIN, forecastModel.getDay5Min());
        values.put(forecastModel.DAY5_MAX, forecastModel.getDay5Max());
        database.insert(ForecastModel.TABLE_NAME, null, values);


    }

    public ForecastModel getOneCityForecast(String cityName) {
        ForecastModel model = new ForecastModel();
        String selectQuery = "SELECT  * FROM " + ForecastModel.TABLE_NAME + " WHERE " + ForecastModel.CITY_COLUMN + " =? ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cityName});
        if (cursor.moveToNext()) {
            model.setCityName(cursor.getString(cursor.getColumnIndex(ForecastModel.CITY_COLUMN)));
            model.setDay1Max(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY1_MAX)));
            model.setDay1Min(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY1_MIN)));

            model.setDay2(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY2)));
            model.setDay2Max(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY2_MAX)));
            model.setDay2Min(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY2_MIN)));

            model.setDay3(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY3)));
            model.setDay3Max(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY3_MAX)));
            model.setDay3Min(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY3_MIN)));

            model.setDay4(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY4)));
            model.setDay4Max(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY4_MAX)));
            model.setDay4Min(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY4_MIN)));

            model.setDay5(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY5)));
            model.setDay5Max(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY5_MAX)));
            model.setDay5Min(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY5_MIN)));
        }
        return model;
    }

    public List<ForecastModel> getForecastForAllCities() {

        List<ForecastModel> models = new ArrayList<>();
        ForecastModel model = new ForecastModel();
        String selectQuery = "SELECT  * FROM " + ForecastModel.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            model.setCityName(cursor.getString(cursor.getColumnIndex(ForecastModel.CITY_COLUMN)));
            model.setDay1Max(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY1_MAX)));
            model.setDay1Min(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY1_MIN)));

            model.setDay2(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY2)));
            model.setDay2Max(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY2_MAX)));
            model.setDay2Min(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY2_MIN)));

            model.setDay3(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY3)));
            model.setDay3Max(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY3_MAX)));
            model.setDay3Min(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY3_MIN)));

            model.setDay4(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY4)));
            model.setDay4Max(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY4_MAX)));
            model.setDay4Min(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY4_MIN)));

            model.setDay5(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY5)));
            model.setDay5Max(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY5_MAX)));
            model.setDay5Min(cursor.getInt(cursor.getColumnIndex(ForecastModel.DAY5_MIN)));
            models.add(model);
        }
        return models;
    }

    public void updateForecast(ForecastModel forecastModel, String cityName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ForecastModel.DAY1_MIN, forecastModel.getDay1Min());
        values.put(ForecastModel.DAY1_MAX, forecastModel.getDay1Max());

        values.put(ForecastModel.DAY2, forecastModel.getDay2());
        values.put(ForecastModel.DAY2_MIN, forecastModel.getDay2Min());
        values.put(forecastModel.DAY2_MAX, forecastModel.getDay2Max());

        values.put(forecastModel.DAY3, forecastModel.getDay3());
        values.put(ForecastModel.DAY3_MIN, forecastModel.getDay3Min());
        values.put(forecastModel.DAY3_MAX, forecastModel.getDay3Max());

        values.put(forecastModel.DAY4, forecastModel.getDay4());
        values.put(ForecastModel.DAY4_MIN, forecastModel.getDay4Min());
        values.put(forecastModel.DAY4_MAX, forecastModel.getDay4Max());

        values.put(forecastModel.DAY5, forecastModel.getDay5());
        values.put(ForecastModel.DAY5_MIN, forecastModel.getDay5Min());
        values.put(forecastModel.DAY5_MAX, forecastModel.getDay5Max());
        db.update(ForecastModel.TABLE_NAME, values, ForecastModel.CITY_COLUMN + " = ?",
                new String[]{cityName});

    }
}
