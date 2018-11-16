package com.example.shazahassan.forecastapp.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shaza Hassan on 16-Nov-18.
 */
public class APIClient {
    private static Retrofit retrofit = null;
    private static String WEATHERBASEURL = "http://api.openweathermap.org/data/2.5/";

    public static Retrofit client() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(WEATHERBASEURL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
