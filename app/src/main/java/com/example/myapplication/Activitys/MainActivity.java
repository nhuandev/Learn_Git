package com.example.myapplication.Activitys;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.Adapters.ViewPagerAdapter;
import com.example.myapplication.R;
import com.example.myapplication.Service.Broadcast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private Broadcast broadcast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNav);

        viewPager2.setAdapter(new ViewPagerAdapter(this));

        broadcast = new Broadcast();

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

        // Bấm vào bottom thì chuyển trang
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    viewPager2.setCurrentItem(0);
                    Log.d("TAG", "onNavigationItemSelected: 1");
                    return true;
                } else if (id == R.id.menu_profile) {
                    viewPager2.setCurrentItem(1);
                    Log.d("TAG", "onNavigationItemSelected: 2");
                    return true;
                } else if (id == R.id.menu_settings) {
                    viewPager2.setCurrentItem(2);
                    Log.d("TAG", "onNavigationItemSelected: 3");
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcast, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcast);
    }
}
