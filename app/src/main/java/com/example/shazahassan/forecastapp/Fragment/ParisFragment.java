package com.example.shazahassan.forecastapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shazahassan.forecastapp.ContactUs;
import com.example.shazahassan.forecastapp.R;
import com.example.shazahassan.forecastapp.SqLite.WeatherDatabaseHelper;
import com.example.shazahassan.forecastapp.SqLite.model.ForecastModel;
import com.example.shazahassan.forecastapp.SqLite.model.WeatherModel;
import com.example.shazahassan.forecastapp.forecast.Forecast;
import com.example.shazahassan.forecastapp.retrofit.APIClient;
import com.example.shazahassan.forecastapp.retrofit.APIWeatherInterface;
import com.example.shazahassan.forecastapp.weather.Weather;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shaza Hassan on 17-Nov-18.
 */
public class ParisFragment extends Fragment {
    View rootView;

    private TextView city, description, tempDegree, dayOfWeek, minTemp, maxTemp,
            day2, day2minTemp, day2MaxTemp,
            day3, day3minTemp, day3MaxTemp,
            day4, day4minTemp, day4MaxTemp,
            day5, day5minTemp, day5MaxTemp,
            pressure, humidity, sunrise, sunset,
            contactUs;
    private APIWeatherInterface apiWeatherInterface;
    private Calendar calendar, currentDate;
    private int day, dayOfMonth, hour, currentDateDay, minute, hour1, minute1, currentTemp;


    private WeatherDatabaseHelper helper, forecastDatabaseHelper;

    private WeatherModel weatherModel;
    private boolean minAdded = false, maxAdded = false;
    private ForecastModel forecastModel;

    public ParisFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.weather_template, container, false);
        helper = new WeatherDatabaseHelper(getContext());
        forecastDatabaseHelper = new WeatherDatabaseHelper(getContext());
        forecastModel = new ForecastModel();
        getReferences(rootView);
        calendar = Calendar.getInstance();
        currentDate = Calendar.getInstance();
        currentDateDay = currentDate.get(Calendar.DAY_OF_MONTH);

        if (isConnected()) {
            apiWeatherInterface = APIClient.client().create(APIWeatherInterface.class);
            getWeather();
            getForecast();
            Toast.makeText(getContext(), "connect", Toast.LENGTH_LONG).show();
        } else {
            weatherModel = helper.getOneCity("Paris");
            showWeatherOffline(weatherModel);
            forecastModel = forecastDatabaseHelper.getOneCityForecast("Paris");
            showForecastOffline(forecastModel);
            Toast.makeText(getContext(), "not connect", Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        return isConnected;
    }

    private void showWeatherOffline(WeatherModel model) {
        //name
        city.setText(model.getCity());
        //day
        int dt = model.getDate();
        Log.v("hourdate", dt + "");
        calendar.setTimeInMillis(dt * 1000L);
        day = calendar.get(Calendar.DAY_OF_WEEK);
        Log.v("hour", day + "");
        setDay(dayOfWeek, day);
        //desc
        description.setText(model.getDescription());
        //main data temp pressure humidity
        tempDegree.setText(Integer.toString(model.getTemp()));
        pressure.setText(Integer.toString(model.getPressure()) + "hPa");
        humidity.setText(Integer.toString(model.getHumidity()) + "%");
        //sunrise and sunset
        sunrise.setText(model.getSunrise());
        sunset.setText(model.getSunset());

    }

    private void showForecastOffline(ForecastModel model) {
        //name
        city.setText(model.getCityName());
        //day
        minTemp.setText(Integer.toString(model.getDay1Min()));
        maxTemp.setText(Integer.toString(model.getDay1Max()));

        int dt = model.getDay2();
        calendar.setTimeInMillis(dt * 1000L);
        Log.v("hour", dt + "");
        day = calendar.get(Calendar.DAY_OF_WEEK);
        Log.v("hour", " day " + day);
        setDay(day2, day);
        day2minTemp.setText(Integer.toString(model.getDay2Min()));
        day2MaxTemp.setText(Integer.toString(model.getDay2Max()));

        dt = model.getDay3();
        Log.v("hour", dt + "");
        calendar.setTimeInMillis(dt * 1000L);
        int day3date = calendar.get(Calendar.DAY_OF_WEEK);
        Log.v("hour", " day " + day3date);
        setDay(day3, day3date);
        day3minTemp.setText(Integer.toString(model.getDay3Min()));
        day3MaxTemp.setText(Integer.toString(model.getDay3Max()));

        dt = model.getDay4();
        calendar.setTimeInMillis(dt * 1000L);
        int day4date = calendar.get(Calendar.DAY_OF_WEEK);
        setDay(day4, day4date);
        day4minTemp.setText(Integer.toString(model.getDay4Min()));
        day4MaxTemp.setText(Integer.toString(model.getDay4Max()));

        dt = model.getDay5();
        calendar.setTimeInMillis(dt * 1000L);
        int day5date = calendar.get(Calendar.DAY_OF_WEEK);
        setDay(day5, day5date);
        day5minTemp.setText(Integer.toString(model.getDay5Min()));
        day5MaxTemp.setText(Integer.toString(model.getDay5Max()));

    }

    private void getReferences(View rootView) {
        city = rootView.findViewById(R.id.city_name);
        description = rootView.findViewById(R.id.description);
        tempDegree = rootView.findViewById(R.id.temp_degree);
        dayOfWeek = rootView.findViewById(R.id.day_of_week);
        minTemp = rootView.findViewById(R.id.min_temp);
        maxTemp = rootView.findViewById(R.id.max_temp);
        pressure = rootView.findViewById(R.id.pressure_number);
        humidity = rootView.findViewById(R.id.humidity_number);
        sunrise = rootView.findViewById(R.id.sunrise_hour);
        sunset = rootView.findViewById(R.id.sunset_hour);
        //day2
        day2 = rootView.findViewById(R.id.day2);
        day2minTemp = rootView.findViewById(R.id.day2_temp_min);
        day2MaxTemp = rootView.findViewById(R.id.day2_temp_max);
        //day3
        day3 = rootView.findViewById(R.id.day3);
        day3minTemp = rootView.findViewById(R.id.day3_temp_min);
        day3MaxTemp = rootView.findViewById(R.id.day3_temp_max);
        //day4
        day4 = rootView.findViewById(R.id.day4);
        day4minTemp = rootView.findViewById(R.id.day4_temp_min);
        day4MaxTemp = rootView.findViewById(R.id.day4_temp_max);
        //day5
        day5 = rootView.findViewById(R.id.day5);
        day5minTemp = rootView.findViewById(R.id.day5_temp_min);
        day5MaxTemp = rootView.findViewById(R.id.day5_temp_max);

        //contact us
        contactUs = rootView.findViewById(R.id.contactUs);
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ContactUs.class));
            }
        });
    }

    private void setDay(TextView dayText, long date) {
        if (date == Calendar.SATURDAY) {
            dayText.setText("Saturday");
        } else if (date == Calendar.SUNDAY) {
            dayText.setText("Sunday");
        } else if (date == Calendar.MONDAY) {
            dayText.setText("Monday");
        } else if (date == Calendar.TUESDAY) {
            dayText.setText("Tuesday");
        } else if (date == Calendar.WEDNESDAY) {
            dayText.setText("Wednesday");
        } else if (date == Calendar.THURSDAY) {
            dayText.setText("Thursday");
        } else if (date == Calendar.FRIDAY) {
            dayText.setText("Friday");
        }

    }

    private void getWeather() {
        Call<Weather> callWeather = apiWeatherInterface.parisWeather();
        callWeather.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                Log.d("TAG", response.code() + "");

                Weather resource = response.body();
                String name = resource.name;
                Log.v("nameCity", name);
                city.setText(name);

                //today name
                long dt = resource.dt;

                calendar.setTimeInMillis(dt * 1000);
                day = calendar.get(Calendar.DAY_OF_WEEK);
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                Log.v("hour", hour + "");
                setDay(dayOfWeek, day);

                //weather desc
                List<Weather.WeatherData> weatherDataList = resource.weather;
                description.setText(weatherDataList.get(0).description);
                //main data temp pressure humidity
                Weather.MainData mainData = resource.main;
                tempDegree.setText(Integer.toString((int) mainData.temp));
                currentTemp = (int) mainData.temp;
                pressure.setText(Integer.toString((int) mainData.pressure) + "hPa");
                humidity.setText(Integer.toString((int) mainData.humidity) + "%");

                //sunrise sunset
                Weather.SysData sysData = resource.sysData;
                long sunriseHour = sysData.sunrise;
                Date date = new Date(sunriseHour * 1000L);
                calendar.setTimeZone(TimeZone.getTimeZone("GMT+1"));
                calendar.setTime(date);
                hour = calendar.get(Calendar.HOUR);
                minute = calendar.get(Calendar.MINUTE);
                if (minute < 10) {
                    sunrise.setText(hour + ":0" + minute + "AM");
                } else {
                    sunrise.setText(hour + ":" + minute + "AM");
                }
                long sunsetHour = sysData.sunset;
                date = new Date(sunsetHour * 1000L);
                calendar.setTime(date);
                hour1 = calendar.get(Calendar.HOUR);
                minute1 = calendar.get(Calendar.MINUTE);
                if (minute1 < 10) {
                    sunset.setText(hour1 + ":0" + minute1 + "PM");

                } else {
                    sunset.setText(hour1 + ":" + minute1 + "PM");
                }
                // save data offline
                if (minute1 < 10) {
                    helper.insertNewData(new WeatherModel((int) mainData.temp, (int) mainData.pressure, (int) mainData.humidity, resource.dt,
                            name, weatherDataList.get(0).description, hour1 + ":0" + minute1 + "PM", hour + ":" + minute + "AM"));
                } else if (minute < 10) {
                    helper.insertNewData(new WeatherModel((int) mainData.temp, (int) mainData.pressure, (int) mainData.humidity, resource.dt,
                            name, weatherDataList.get(0).description, hour1 + ":" + minute1 + "PM", hour + ":0" + minute + "AM"));
                } else {
                    helper.insertNewData(new WeatherModel((int) mainData.temp, (int) mainData.pressure, (int) mainData.humidity, resource.dt,
                            name, weatherDataList.get(0).description, hour1 + ":" + minute1 + "PM", hour + ":" + minute + "AM"));
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.v("ERROR", t.getLocalizedMessage());
                call.cancel();
            }
        });
    }

    private void getForecast() {
        Call<Forecast> callForecast = apiWeatherInterface.parisForecast();
        callForecast.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Log.d("TAG", response.code() + "");
                Forecast resource = response.body();
                List<Forecast.listData> listDataList = resource.list;
                forecastModel.setCityName("Paris");
                //day1
                // temp at 3:00 am as min Temp
                // temp at 12:00 pm as max Temp
                for (Forecast.listData listData : listDataList) {
                    Forecast.mainData mainData = listData.main;
                    long dt = listData.dt;
                    Date date = new Date(dt * 1000L);
                    // the format of your date
                    // format of the date
                    calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
                    calendar.setTime(date);
                    day = calendar.get(Calendar.DAY_OF_WEEK);
                    dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    hour = calendar.get(Calendar.HOUR_OF_DAY);

                    if (dayOfMonth == currentDateDay) {
                        Log.v("Today", day + "");
                        Log.v("Today", hour + "");
                        if (hour == 3) {
                            minTemp.setText(Integer.toString((int) mainData.temp));
                            forecastModel.setDay1Min((int) mainData.temp);
                            minAdded = true;
                        } else if (hour == 12) {
                            maxTemp.setText(Integer.toString((int) mainData.temp));
                            maxAdded = true;
                            forecastModel.setDay1Max((int) mainData.temp);
                        }
                    } else if ((dayOfMonth - currentDateDay) == 1) {
                        forecastModel.setDay2((int) listData.dt);
                        setDay(day2, day);
                        if (hour == 3) {
                            day2minTemp.setText(Integer.toString((int) mainData.temp));
                            forecastModel.setDay2Min((int) mainData.temp);
                            if (!minAdded) {
                                if (((int) mainData.temp) < currentTemp) {
                                    minTemp.setText(Integer.toString((int) mainData.temp));
                                    forecastModel.setDay1Min((int) mainData.temp);
                                } else {
                                    minTemp.setText(Integer.toString(currentTemp));
                                    forecastModel.setDay1Max(currentTemp);
                                }
                            }
                        } else if (hour == 12) {
                            day2MaxTemp.setText(Integer.toString((int) mainData.temp));
                            forecastModel.setDay2Max((int) mainData.temp);
                            if (!maxAdded) {
                                if (((int) mainData.temp) > currentTemp) {
                                    maxTemp.setText(Integer.toString((int) mainData.temp));
                                    forecastModel.setDay1Max((int) mainData.temp);
                                } else {
                                    maxTemp.setText(Integer.toString(currentTemp));
                                    forecastModel.setDay1Max(currentTemp);
                                }
                            }
                        }
                    } else if ((dayOfMonth - currentDateDay) == 2) {
                        forecastModel.setDay3((int) listData.dt);
                        setDay(day3, day);
                        if (hour == 3) {
                            day3minTemp.setText(Integer.toString((int) mainData.temp));
                            forecastModel.setDay3Min((int) mainData.temp);
                        } else if (hour == 12) {
                            day3MaxTemp.setText(Integer.toString((int) mainData.temp));
                            forecastModel.setDay3Max((int) mainData.temp);
                        }
                    } else if ((dayOfMonth - currentDateDay) == 3) {
                        forecastModel.setDay4((int) listData.dt);
                        setDay(day4, day);
                        if (hour == 3) {
                            day4minTemp.setText(Integer.toString((int) mainData.temp));
                            forecastModel.setDay4Min((int) mainData.temp);
                        } else if (hour == 12) {
                            day4MaxTemp.setText(Integer.toString((int) mainData.temp));
                            forecastModel.setDay4Max((int) mainData.temp);
                        }
                    } else if ((dayOfMonth - currentDateDay) == 4) {
                        forecastModel.setDay5((int) listData.dt);
                        setDay(day5, day);
                        if (hour == 3) {
                            day5minTemp.setText(Integer.toString((int) mainData.temp));
                            forecastModel.setDay5Min((int) mainData.temp);
                        } else if (hour == 12) {
                            day5MaxTemp.setText(Integer.toString((int) mainData.temp));
                            forecastModel.setDay5Max((int) mainData.temp);
                            forecastDatabaseHelper.insertNewDataForecast(forecastModel);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.v("nameCity", t.getLocalizedMessage());
                call.cancel();
            }
        });
    }
}
