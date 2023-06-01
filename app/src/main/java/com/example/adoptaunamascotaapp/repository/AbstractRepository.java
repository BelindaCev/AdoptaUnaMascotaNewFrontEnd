package com.example.adoptaunamascotaapp.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AbstractRepository {
    protected  Retrofit retrofit;

    public AbstractRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.144:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
