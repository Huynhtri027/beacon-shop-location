package com.example.sebo.shoplocationmobile.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Sebo on 2015-11-27.
 */
public class SimpleRestAdapter {

    public static final String API_URL = "http://webapp-juraszek.rhcloud.com";

    private Retrofit retrofitAdapter;

    public SimpleRestAdapter(String url) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        // Add the interceptor to OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(getDefaultInterceptor());

        retrofitAdapter = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    private Interceptor getDefaultInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().addHeader("Content-Type", "application/json").build();
                return chain.proceed(newRequest);
            }
        };
    }

    public Retrofit getRetrofitAdapter() {
        return retrofitAdapter;
    }

}
