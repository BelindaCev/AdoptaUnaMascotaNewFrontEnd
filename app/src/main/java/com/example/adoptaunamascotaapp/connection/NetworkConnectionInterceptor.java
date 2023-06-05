package com.example.adoptaunamascotaapp.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class NetworkConnectionInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        try {
            Request.Builder builder = chain.request().newBuilder();
            return chain.proceed(builder.build());
        }catch (Exception e) {
            System.err.println("Error HTTP: " + e);
            throw e;
        }
    }


}
