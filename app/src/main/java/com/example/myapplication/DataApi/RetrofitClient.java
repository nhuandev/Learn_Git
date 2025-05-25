package com.example.myapplication.DataApi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static retrofit2.Retrofit instance;

    public  static retrofit2.Retrofit getInstance(String BASEURL){

        ExecutorService executor = Executors.newFixedThreadPool(5); // Số luồng thực thi yêu cầu
        Dispatcher dispatcher = new Dispatcher(executor);
        dispatcher.setMaxRequests(5); // Số yêu cầu tối đa được thực thi cùng lúc

        OkHttpClient client = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .addInterceptor(new RetryInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        if (instance == null){
            instance = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return instance;
    }
}
