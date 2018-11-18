package com.example.shazahassan.forecastapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shazahassan.forecastapp.R;
import com.example.shazahassan.forecastapp.SqLite.DatabaseHelper;
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
public class CairoFragment extends Fragment {

    View rootView;

    private TextView city,description,tempDegree,dayOfWeek,minTemp,maxTemp,day2,day2minTemp,day2MaxTemp
            ,day3,day3minTemp,day3MaxTemp
            ,day4,day4minTemp,day4MaxTemp, day5, day5minTemp, day5MaxTemp, pressure, humidity, sunrise, sunset;
    private APIWeatherInterface apiWeatherInterface;
    private Calendar calendar, currentDate;
    private int day, dayOfMonth, hour, currentDateDay, minute, hour1, minute1;
    private DatabaseHelper helper;

    public CairoFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.weather_template, container, false);
        helper = new DatabaseHelper(getContext());
        getReferences(rootView);
        calendar = Calendar.getInstance();
        currentDate = Calendar.getInstance();
        currentDateDay = currentDate.get(Calendar.DAY_OF_MONTH);

        apiWeatherInterface = APIClient.client().create(APIWeatherInterface.class);

        getWeather();
        getForecast();
        return rootView;
    }

    private void getReferences(View rootView){
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

    private void getWeather(){
        Call<Weather> callWeather = apiWeatherInterface.cairoWeather();
        callWeather.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                Log.d("TAG",response.code()+"");

                Weather resource = response.body();
                String name = resource.name;
                Log.v("nameCity",name);
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
                tempDegree.setText(Integer.toString(mainData.temp));
                pressure.setText(Integer.toString(mainData.pressure) + "hPa");
                humidity.setText(Integer.toString(mainData.humidity) + "%");

                //sunrise sunset
                Weather.SysData sysData = resource.sysData;
                long sunriseHour = sysData.sunrise;
                Date date = new Date(sunriseHour * 1000L);
                calendar.setTime(date);
                hour = calendar.get(Calendar.HOUR);
                minute = calendar.get(Calendar.MINUTE);
                sunrise.setText(hour + ":" + minute + "AM");
                long sunsetHour = sysData.sunset;
                date = new Date(sunsetHour * 1000L);
                calendar.setTime(date);
                hour1 = calendar.get(Calendar.HOUR);
                minute1 = calendar.get(Calendar.MINUTE);
                sunset.setText(hour + ":" + minute + "PM");

                // save data offline
                helper.insertNewData(new WeatherModel(mainData.temp, mainData.pressure, mainData.humidity,
                        name, weatherDataList.get(0).description, hour1 + ":" + minute1 + "PM", hour + ":" + minute + "AM"));
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.v("ERROR", t.getLocalizedMessage());
                call.cancel();
            }
        });
    }

    private void getForecast(){
        Call<Forecast> callForecast = apiWeatherInterface.cairoForecast();
        callForecast.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Log.d("TAG",response.code()+"");
                Forecast resource = response.body();
                List<Forecast.listData> listDataList = resource.list;
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
                        } else if (hour == 12) {
                            maxTemp.setText(Integer.toString((int) mainData.temp));
                        }
                    } else if ((dayOfMonth - currentDateDay) == 1) {
                        setDay(day2, day);
                        if (hour == 3) {
                            day2minTemp.setText(Integer.toString((int) mainData.temp));
                        } else if (hour == 12) {
                            day2MaxTemp.setText(Integer.toString((int) mainData.temp));
                        }
                    } else if ((dayOfMonth - currentDateDay) == 2) {
                        setDay(day3, day);
                        if (hour == 3) {
                            day3minTemp.setText(Integer.toString((int) mainData.temp));
                        } else if (hour == 12) {
                            day3MaxTemp.setText(Integer.toString((int) mainData.temp));
                        }
                    } else if ((dayOfMonth - currentDateDay) == 3) {
                        setDay(day4, day);
                        if (hour == 3) {
                            day4minTemp.setText(Integer.toString((int) mainData.temp));
                        } else if (hour == 12) {
                            day4MaxTemp.setText(Integer.toString((int) mainData.temp));
                        }
                    } else if ((dayOfMonth - currentDateDay) == 4) {
                        setDay(day5, day);
                        if (hour == 3) {
                            day5minTemp.setText(Integer.toString((int) mainData.temp));
                        } else if (hour == 12) {
                            day5MaxTemp.setText(Integer.toString((int) mainData.temp));
                        }
                    }
                }
//                Forecast.mainData mainDataMin = listDataList.get(0).main;
//                //min
////                minTemp.setText(Integer.toString((int) mainDataMin.temp));
//                //max
//                Forecast.mainData mainDataMax = listDataList.get(3).main;
////                maxTemp.setText(Integer.toString((int) mainDataMax.temp));
//
//                //day2
//                Forecast.mainData mainDataMinDay2 = listDataList.get(8).main;
//                //min
////                .setText(Integer.toString((int) mainDataMinDay2.temp));
//                //max
//                Forecast.mainData mainDataMaxDay2 = listDataList.get(11).main;
//                day2MaxTemp.setText(Integer.toString((int) mainDataMaxDay2.temp));
//
//                long unixSeconds = listDataList.get(8).dt;
//                // convert seconds to milliseconds
//                Date date = new Date(unixSeconds*1000L);
//                // the format of your date
//                // format of the date
//                SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
//                jdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
//                String java_date = jdf.format(date);
//                calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
//                calendar.setTime(date);
//                day = calendar.get(Calendar.DAY_OF_WEEK);
//                hour = calendar.get(Calendar.HOUR_OF_DAY);
//                Log.v("hourNow",hour+"");

//                //day3
//                Forecast.mainData mainDataMinDay3 = listDataList.get(16).main;
//                day3minTemp.setText(Integer.toString((int) mainDataMinDay3.temp));
//                Forecast.mainData mainDataMaxDay3 = listDataList.get(19).main;
//                day3MaxTemp.setText(Integer.toString((int) mainDataMaxDay3.temp));
//                calendar.setTimeInMillis(listDataList.get(16).dt*1000);
//                day = calendar.get(Calendar.DAY_OF_WEEK);
//                setDay(day3,day);

//                //day4
//                Forecast.mainData mainDataMinDay4 = listDataList.get(24).main;
//                day4minTemp.setText(Integer.toString((int) mainDataMinDay4.temp));
//                Forecast.mainData mainDataMaxDay4 = listDataList.get(27).main;
//                day4MaxTemp.setText(Integer.toString((int) mainDataMaxDay4.temp));
//                calendar.setTimeInMillis(listDataList.get(24).dt*1000);
//                day = calendar.get(Calendar.DAY_OF_WEEK);
//                setDay(day4,day);

//                //day5
//                Forecast.mainData mainDataMinDay5 = listDataList.get(32).main;
//                day5minTemp.setText(Integer.toString((int) mainDataMinDay5.temp));
//                Forecast.mainData mainDataMaxDay5 = listDataList.get(35).main;
//                day5MaxTemp.setText(Integer.toString((int) mainDataMaxDay5.temp));
//                calendar.setTimeInMillis(listDataList.get(32).dt*1000);
//                day = calendar.get(Calendar.DAY_OF_WEEK);
//                setDay(day5,day);
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.v("nameCity",t.getLocalizedMessage());
                call.cancel();
            }
        });
    }
}
