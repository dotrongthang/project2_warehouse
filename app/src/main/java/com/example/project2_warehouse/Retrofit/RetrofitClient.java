package com.example.project2_warehouse.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit retrofit = null;
    public static Retrofit getClient (String baseUrl){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient builder = new OkHttpClient.Builder()
                                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                                    .retryOnConnectionFailure(true)
                                    .build();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                ;

        httpClient.addInterceptor(logging);  // <-- this is the important line!
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
        return retrofit;
    }
}
