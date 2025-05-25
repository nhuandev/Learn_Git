package com.example.myapplication.DataApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        int maxRetryCount = 3; // Số lần retry tối đa
        int retryDelayMillis = 3000; // Thời gian chờ giữa các retry (3 giây)

        for (int retryCount = 0; retryCount < maxRetryCount; retryCount++) {
            if (response.code() == 429) {
                try {
                    Thread.sleep(retryDelayMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response.close(); // Đóng response hiện tại
                response = chain.proceed(request); // Gửi lại yêu cầu
            } else {
                break;
            }
        }

        return response;
    }
}
