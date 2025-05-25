package com.example.myapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activitys.LifeCycleActivity;
import com.example.myapplication.Activitys.UserActivity;
import com.example.myapplication.OOP.Dog;
import com.example.myapplication.R;

public class HomeFragment extends Fragment {
    private Context context;
    private Button btn, btnActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        initUI(view);
        return view;
    }

    private void initUI(View view){
        btn = view.findViewById(R.id.btnUser);
        btnActivity = view.findViewById(R.id.btnActivity);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserActivity.class);
            startActivity(intent);
        });
        btnActivity = view.findViewById(R.id.btnActivity);
        btnActivity.setOnClickListener(v -> {
            Intent intent = new Intent(context, LifeCycleActivity.class);
            startActivity(intent);
        });
    }

}
