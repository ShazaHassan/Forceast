package com.example.shazahassan.forecastapp.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shazahassan.forecastapp.R;
import com.example.shazahassan.forecastapp.forecast.Forecast;
import com.example.shazahassan.forecastapp.retrofit.APIClient;
import com.example.shazahassan.forecastapp.retrofit.APIWeatherInterface;
import com.example.shazahassan.forecastapp.weather.Weather;

import java.util.Calendar;
import java.util.List;

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
            ,day4,day4minTemp,day4MaxTemp
            ,day5,day5minTemp,day5MaxTemp;
    private APIWeatherInterface apiWeatherInterface;

    public CairoFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.weather_template, container, false);
         getReferences(rootView);

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

    private void setDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if(day == Calendar.SATURDAY){
            dayOfWeek.setText("Saturday");
            day2.setText("Sunday");
            day3.setText("Monday");
            day4.setText("Tuesday");
            day5.setText("Wednesday");
        } else if( day == Calendar.SUNDAY){
            dayOfWeek.setText("Sunday");
            day2.setText("Monday");
            day3.setText("Tuesday");
            day4.setText("Wednesday");
            day5.setText("Thursday");
        } else if (day ==Calendar.MONDAY){
            dayOfWeek.setText("Monday");
            day2.setText("Tuesday");
            day3.setText("Wednesday");
            day4.setText("Thursday");
            day5.setText("Friday");
        }else if (day ==Calendar.TUESDAY){
            dayOfWeek.setText("Tuesday");
            day2.setText("Wednesday");
            day3.setText("Thursday");
            day4.setText("Friday");
            day5.setText("Saturday");
        }else if (day ==Calendar.WEDNESDAY){
            dayOfWeek.setText("Wednesday");
            day2.setText("Thursday");
            day3.setText("Friday");
            day4.setText("Saturday");
            day5.setText("Sunday");
        }else if (day ==Calendar.THURSDAY){
            dayOfWeek.setText("Thursday");
            day2.setText("Friday");
            day3.setText("Saturday");
            day4.setText("Sunday");
            day5.setText("Monday");
        }else if (day ==Calendar.FRIDAY){
            dayOfWeek.setText("Friday");
            day2.setText("Saturday");
            day3.setText("Sunday");
            day4.setText("Monday");
            day5.setText("Tuesday");
        }

        Log.v("dayOfWeek",Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));

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
                List<Weather.WeatherData> weatherDataList = resource.weather;
                Log.v("weatherData", weatherDataList.get(0).description);
                description.setText(weatherDataList.get(0).description);
                setDay();
                Weather.MainData mainData = resource.main;

                double tempInC = mainData.temp - 273.15;
                tempDegree.setText(Integer.toString((int) tempInC));
                Log.v("Temp",Integer.toString((int) tempInC));

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.v("nameCity",t.getLocalizedMessage());
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
                Forecast.mainData mainDataMin = listDataList.get(0).main;
                double tempInCMin = mainDataMin.temp - 273.15;
                minTemp.setText(Integer.toString((int) tempInCMin));
                Log.v("Temp2",Integer.toString((int) tempInCMin));
                Forecast.mainData mainDataMax = listDataList.get(3).main;
                double tempInCMax = mainDataMax.temp - 273.15;
                maxTemp.setText(Integer.toString((int) tempInCMax));

                //day2
                Forecast.mainData mainDataMinDay2 = listDataList.get(8).main;
                double tempInCMinDay2 = mainDataMinDay2.temp - 273.15;
                day2minTemp.setText(Integer.toString((int) tempInCMinDay2));
                Log.v("Temp2",Integer.toString((int) tempInCMin));
                Forecast.mainData mainDataMaxDay2 = listDataList.get(11).main;
                double tempInCMaxDay2 = mainDataMaxDay2.temp - 273.15;
                day2MaxTemp.setText(Integer.toString((int) tempInCMaxDay2));

                //day3
                Forecast.mainData mainDataMinDay3 = listDataList.get(16).main;
                double tempInCMinDay3 = mainDataMinDay3.temp - 273.15;
                day3minTemp.setText(Integer.toString((int) tempInCMinDay3));
                Log.v("Temp2",Integer.toString((int) tempInCMin));
                Forecast.mainData mainDataMaxDay3 = listDataList.get(19).main;
                double tempInCMaxDay3 = mainDataMaxDay3.temp - 273.15;
                day3MaxTemp.setText(Integer.toString((int) tempInCMaxDay3));

                //day4
                Forecast.mainData mainDataMinDay4 = listDataList.get(24).main;
                double tempInCMinDay4 = mainDataMinDay4.temp - 273.15;
                day4minTemp.setText(Integer.toString((int) tempInCMinDay4));
                Log.v("Temp2",Integer.toString((int) tempInCMin));
                Forecast.mainData mainDataMaxDay4 = listDataList.get(27).main;
                double tempInCMaxDay4 = mainDataMaxDay4.temp - 273.15;
                day4MaxTemp.setText(Integer.toString((int) tempInCMaxDay4));

                //day5
                Forecast.mainData mainDataMinDay5 = listDataList.get(32).main;
                double tempInCMinDay5 = mainDataMinDay5.temp - 273.15;
                day5minTemp.setText(Integer.toString((int) tempInCMinDay5));
                Log.v("Temp2",Integer.toString((int) tempInCMin));
                Forecast.mainData mainDataMaxDay5 = listDataList.get(35).main;
                double tempInCMaxDay5 = mainDataMaxDay5.temp - 273.15;
                day5MaxTemp.setText(Integer.toString((int) tempInCMaxDay5));
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.v("nameCity",t.getLocalizedMessage());
                call.cancel();
            }
        });
    }
}
