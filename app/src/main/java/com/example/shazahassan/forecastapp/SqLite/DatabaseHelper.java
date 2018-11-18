package com.example.shazahassan.forecastapp.SqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.shazahassan.forecastapp.SqLite.model.WeatherModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaza Hassan on 17-Nov-18.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "forecast.db";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(WeatherModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherModel.TABLE_NAME);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void insertNewData(WeatherModel weatherModel) {
        SQLiteDatabase database = this.getWritableDatabase();
        List<WeatherModel> models = new ArrayList<>();
        models = getWeatherForAllCities();
        if (models.size() != 0) {
            for (WeatherModel model : models) {
                if (model.getCity().equals(weatherModel.getCity())) {

                } else {
                    save(weatherModel, database);
                }
            }
        } else {
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
        long rowInserted = database.insert(WeatherModel.TABLE_NAME, null, values);

        if (rowInserted != -1)
            Toast.makeText(context, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
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
}
