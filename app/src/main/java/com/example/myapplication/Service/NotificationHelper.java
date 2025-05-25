package com.example.myapplication.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

public class NotificationHelper {

    public static final String CHANNEL_ID = "MyBasicNotificationChannel";
    private static final String CHANNEL_NAME = "Basic Notifications";
    private static final String CHANNEL_DESCRIPTION = "Channel for general app notifications";
    private static final String TAG = "NotificationHelper";

    public static void createNotificationChannel(Context context) {
        // Chỉ tạo kênh nếu phiên bản Android là Oreo (API 26) trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT // Mức độ quan trọng (HIGH, LOW, MIN, MAX)
            );
            channel.setDescription(CHANNEL_DESCRIPTION);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                try {
                    manager.createNotificationChannel(channel);
                    Log.d(TAG, "Notification Channel '" + CHANNEL_ID + "' created successfully.");
                } catch (Exception e) {
                    Log.e(TAG, "Error creating Notification Channel: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "NotificationManager is null. Cannot create channel.");
            }
        } else {
            Log.d(TAG, "Android version is below Oreo, Notification Channel not required.");
        }
    }
}