package com.example.myapplication.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.Activitys.MainActivity;

public class MyForegroundService extends Service {

    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final String CHANNEL_NAME = "My Foreground Service Channel"; // Tên kênh hiển thị cho người dùng
    private static final String TAG = "MyForegroundService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Foreground Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Luôn gọi createNotificationChannel() trước khi gọi startForeground()
        createNotificationChannel();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        // FLAG_IMMUTABLE là bắt buộc cho PendingIntent từ Android S (API 31) trở lên
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        // Sử dụng một icon drawable của riêng bạn nếu có, ví dụ R.drawable.ic_launcher_foreground
        // Nếu không có, android.R.drawable.ic_media_play vẫn hoạt động nhưng không đẹp mắt
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("My Foreground Service")
                .setContentText("Performing long-running operation...")
                .setSmallIcon(android.R.drawable.ic_media_play) // Hoặc R.drawable.your_custom_icon
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Đảm bảo mức độ ưu tiên đủ cao
                .build();

        try {
            startForeground(1, notification); // Unique ID cho Notification
            Log.d(TAG, "startForeground() called successfully.");
        } catch (Exception e) {
            Log.e(TAG, "Error calling startForeground(): " + e.getMessage());
            e.printStackTrace();
        }


        // Thực hiện tác vụ chạy dài ở đây trong một luồng riêng
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                Log.d(TAG, "Foreground service running: " + i);
                try {
                    Thread.sleep(1000); // Giả lập công việc
                } catch (InterruptedException e) {
                    Log.e(TAG, "Foreground service thread interrupted: " + e.getMessage());
                    e.printStackTrace();
                    Thread.currentThread().interrupt(); // Re-interrupt the thread
                }
            }
            Log.d(TAG, "Foreground service task finished. Stopping self.");
            stopSelf(); // Dừng service khi hoàn thành công việc
        }).start();



        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Foreground Service onDestroy");
        // Đảm bảo dừng foreground state khi service bị hủy
        stopForeground(true); // true để loại bỏ notification
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Đây không phải là Bound Service
    }

    private void createNotificationChannel() {
        // Chỉ tạo kênh nếu phiên bản Android là Oreo (API 26) trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME, // Tên kênh hiển thị cho người dùng
                    NotificationManager.IMPORTANCE_DEFAULT // Mức độ quan trọng mặc định
            );
            // Có thể thêm mô tả cho kênh
            serviceChannel.setDescription("This is the channel for My Foreground Service notifications.");

            NotificationManager manager = getSystemService(NotificationManager.class);

            if (manager != null) {
                try {
                    manager.createNotificationChannel(serviceChannel);
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