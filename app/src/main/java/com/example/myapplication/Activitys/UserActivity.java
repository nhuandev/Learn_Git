package com.example.myapplication.Activitys;

import static androidx.fragment.app.FragmentManager.TAG;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    private ActivityUserBinding binding;

    private ApiService apiService;

    private static final String TAG = "TAGSS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Tạo retrofit
        apiService = RetrofitClient.getInstance("https://jsonplaceholder.typicode.com/").create(ApiService.class);

        // Khởi tạo RecyclerView
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));

        // Gọi API
        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    binding.rvUsers.setAdapter(new UserAdapter(users));
                } else {
                    Toast.makeText(UserActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("API Error", t.getMessage() != null ? t.getMessage() : "Unknown error");
                Toast.makeText(UserActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
            }
        });
    }

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
        Log.d(TAG, "onDestroy() 1 called");
        // Lưu ý: TextView sẽ không hiển thị trạng thái này lâu vì Activity sắp bị hủy
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() 1 called");
    }

}
