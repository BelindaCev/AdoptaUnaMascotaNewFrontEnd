package com.example.adoptaunamascotaapp.repository;

import android.content.Context;

import com.example.adoptaunamascotaapp.connection.NetworkConnectionInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AbstractRepository {
    protected  Retrofit retrofit;

    public AbstractRepository() {
        OkHttpClient.Builder oktHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new NetworkConnectionInterceptor());


        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .client(oktHttpClient.build())
                .build();
    }
}
