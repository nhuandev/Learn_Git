package com.example.myapplication.Activitys;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Adapters.UserAdapter;
import com.example.myapplication.DataApi.ApiService;
import com.example.myapplication.DataApi.RetrofitClient;
import com.example.myapplication.Models.User;
import com.example.myapplication.databinding.ActivityUserBinding;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable; // Import Disposable
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    private ActivityUserBinding binding;

    ApiService apiService;

    // Khởi tạo CompositeDisposable ở đây
    UserAdapter userAdapter;

    private static final String TAG = "TAGSS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() 1 create");
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Tạo retrofit
        apiService = RetrofitClient.getInstance("https://jsonplaceholder.typicode.com/").create(ApiService.class);

        // Khởi tạo RecyclerView
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));

        // Gọi API
        getData();
    }

    private void getData() {
        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<User> users = response.body();
                    binding.rvUsers.setAdapter(new UserAdapter(users));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

//    private void getData() {
//        // Gọi API
//        Call<List<User>> call = apiService.getUsers();
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<User> users = response.body();
//                    binding.rvUsers.setAdapter(new UserAdapter(users));
//                } else {
//                    Toast.makeText(UserActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Log.e("API Error", t.getMessage() != null ? t.getMessage() : "Unknown error");
//                Toast.makeText(UserActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() 1 called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() 1 called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() 1 called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() 1 called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Rất quan trọng: Hủy bỏ tất cả các Disposable khi Activity bị hủy
        Log.d(TAG, "onDestroy() 1 called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() 1 called");
    }
}