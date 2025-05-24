package com.example.myapplication.DataApi;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static retrofit2.Retrofit instance;
    public  static retrofit2.Retrofit getInstance(String BASEURL){
        if (instance == null){
            instance = new retrofit2.Retrofit.Builder()
                .baseUrl(BASEURL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return instance;
    }
}
