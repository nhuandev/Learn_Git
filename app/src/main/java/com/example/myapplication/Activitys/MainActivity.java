package com.example.myapplication.Activitys;

import android.Manifest; // Import class Manifest
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager; // Import PackageManager
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast; // Import Toast for messages

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat; // Import ActivityCompat
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat; // Import ContextCompat
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.Adapters.ViewPagerAdapter;
import com.example.myapplication.R;
import com.example.myapplication.Service.Broadcast;
import com.example.myapplication.Service.MyBackgroundService;
import com.example.myapplication.Service.MyForegroundService;
import com.example.myapplication.Service.NotificationHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private Broadcast broadcast; // Consider if this BroadcastReceiver needs to be static or registered dynamically

    private static final int NOTIFICATION_ID = 1;
    // Request code for the POST_NOTIFICATIONS permission
    private static final int REQUEST_POST_NOTIFICATIONS_PERMISSION = 101;
    private static final String TAG = "MainActivity"; // For logging

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNav);
        broadcast = new Broadcast(); // Initialize BroadcastReceiver

        viewPager2.setAdapter(new ViewPagerAdapter(this));

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.menu_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.menu_settings);
                        break;
                    default:
                        bottomNavigationView.setSelectedItemId(R.id.menu_home);
                        break;
                }
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    viewPager2.setCurrentItem(0);
                    return true;
                } else if (id == R.id.menu_profile) {
                    viewPager2.setCurrentItem(1);
                    return true;
                } else if (id == R.id.menu_settings) {
                    viewPager2.setCurrentItem(2);
                    return true;
                }
                return false;
            }
        });

        // --- Foreground Service Buttons ---
        Button startForegroundServiceButton = findViewById(R.id.startForegroundServiceButton);
        Button stopForegroundServiceButton = findViewById(R.id.stopForegroundServiceButton);

        startForegroundServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this, MyForegroundService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent);
                } else {
                    startService(serviceIntent);
                }
            }
        });

        stopForegroundServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this, MyForegroundService.class);
                stopService(serviceIntent);
            }
        });

        // --- Background Service Button ---
        Button startBackgroundServiceButton = findViewById(R.id.startBackgroundServiceButton);
        startBackgroundServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this, MyBackgroundService.class);
                startService(serviceIntent);
            }
        });

        // --- Notification Logic ---
        // Ensure the Notification Channel is created when the app starts
        NotificationHelper.createNotificationChannel(this);

        Button showNotificationButton = findViewById(R.id.showNotificationButton);
        showNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the new method to handle permission request before showing notification
                requestNotificationPermissionAndShow();
            }
        });
    }

    // --- New Method for Runtime Permission Request ---
    private void requestNotificationPermissionAndShow() {
        // Check if Android version is 13 (TIRAMISU) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check if the permission is already granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED) {
                // Permission already granted, proceed to show notification
                Log.d(TAG, "POST_NOTIFICATIONS permission already granted.");
                showSimpleNotification();
            } else {
                // Permission not granted, request it from the user
                Log.d(TAG, "Requesting POST_NOTIFICATIONS permission.");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_POST_NOTIFICATIONS_PERMISSION);
            }
        } else {
            // For Android versions below 13, permission is granted automatically via Manifest
            Log.d(TAG, "Android version < 13, POST_NOTIFICATIONS permission not required at runtime.");
            showSimpleNotification();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_POST_NOTIFICATIONS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // User granted the permission, now show the notification
                Log.d(TAG, "POST_NOTIFICATIONS permission granted by user.");
                showSimpleNotification();
            } else {
                // User denied the permission
                Log.w(TAG, "POST_NOTIFICATIONS permission denied by user.");
                Toast.makeText(this, "Cannot show notification without permission.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // --- Original showSimpleNotification method, with an added check ---
    private void showSimpleNotification() {
        // Add a final check for permission before posting (good practice)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Attempted to show notification without POST_NOTIFICATIONS permission after request.");
            Toast.makeText(this, "Error: Notification permission not granted.", Toast.LENGTH_LONG).show();
            return; // Exit if permission is still not granted
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background) // Make sure this icon exists and is suitable
                .setContentTitle("Thông báo mới!")
                .setContentText("Đây là nội dung của thông báo đơn giản.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Make sure your Broadcast class is correctly named and declared in AndroidManifest.xml
        // Also, it's better to use a specific action name for your app, not a generic one.
        Intent snoozeIntent = new Intent(this, Broadcast.class);
        snoozeIntent.setAction("com.example.myapplication.ACTION_SNOOZE"); // Use a unique action string
        snoozeIntent.putExtra("NOTIFICATION_ID", NOTIFICATION_ID);
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(this,
                0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.ic_launcher_background, "Tạm ẩn", snoozePendingIntent); // Ensure this icon exists

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        try {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
            Log.d(TAG, "Notification ID " + NOTIFICATION_ID + " shown successfully.");
        } catch (SecurityException e) {
            Log.e(TAG, "SecurityException when showing notification: " + e.getMessage());
            Toast.makeText(this, "Security error showing notification: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "General error showing notification: " + e.getMessage());
            Toast.makeText(this, "Error showing notification: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        // It's generally better to register/unregister receivers in onResume/onPause for UI-related receivers
        // or ensure Broadcast is a static inner class or separate class for dynamic registration.
        registerReceiver(broadcast, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "onPause main: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcast);
    }
}