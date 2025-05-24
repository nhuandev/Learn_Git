package com.example.myapplication.Activitys;

import android.content.DialogInterface; // Import cho AlertDialog
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog; // Import cho AlertDialog
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class LifeCycleActivity extends AppCompatActivity {
    private static final String TAG = "LifeCycleActivityTag"; // TAG riêng cho Logcat
    private static final String KEY_EDIT_TEXT_DATA = "editTextData"; // Key để lưu trạng thái EditText

    private TextView textViewStatus;
    private EditText editTextData;
    private Button buttonStartNewActivity, buttonShowDialog, buttonFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle); // Đảm bảo khớp với tên file layout của bạn
        initUI();
        eventClick();

        Log.d(TAG, "onCreate() called");
        textViewStatus.setText("Trạng thái Activity: onCreate()");
        
        // Khôi phục trạng thái từ savedInstanceState
        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(KEY_EDIT_TEXT_DATA);
            editTextData.setText(savedText);
            Log.d(TAG, "onCreate() - Khôi phục dữ liệu EditText: " + savedText);
        }
    }

    private void initUI(){
        textViewStatus = findViewById(R.id.textViewStatus);
        editTextData = findViewById(R.id.editTextData);
        buttonStartNewActivity = findViewById(R.id.buttonStartNewActivity);
        buttonShowDialog = findViewById(R.id.buttonShowDialog);
        buttonFinish = findViewById(R.id.buttonFinish);
    }

    private void eventClick(){
        // Thiết lập listener cho các nút
        buttonStartNewActivity.setOnClickListener(v -> {
            // Khởi chạy UserActivity (hoặc một Activity khác của bạn)
            // Đảm bảo UserActivity đã được khai báo trong AndroidManifest.xml
            Intent intent = new Intent(LifeCycleActivity.this, UserActivity.class);
            startActivity(intent);
        });

        buttonShowDialog.setOnClickListener(v -> {
            // Hiển thị một AlertDialog
            new AlertDialog.Builder(this)
                .setTitle("Dialog Test")
                .setMessage("Đây là một dialog. Quan sát Logcat và TextView trạng thái khi dialog xuất hiện và biến mất.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                        // Dialog sẽ tự đóng khi nhấn OK
                   }
                }).show();
        });

        buttonFinish.setOnClickListener(v -> {
            // Kết thúc Activity này
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
        textViewStatus.setText("Trạng thái Activity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        textViewStatus.setText("Trạng thái Activity: onResume() (Đang hoạt động)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
        textViewStatus.setText("Trạng thái Activity: onPause() (Tạm dừng)");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
        textViewStatus.setText("Trạng thái Activity: onStop() (Đã dừng)");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
        textViewStatus.setText("Trạng thái Activity: onDestroy() (Đã hủy)");
        // Lưu ý: TextView sẽ không hiển thị trạng thái này lâu vì Activity sắp bị hủy
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() called");
        textViewStatus.setText("Trạng thái Activity: onRestart()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState() called");
        // Lưu nội dung của EditText vào Bundle
        outState.putString(KEY_EDIT_TEXT_DATA, editTextData.getText().toString());
    }

    // onRestoreInstanceState được gọi sau onStart()
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState() called");
        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(KEY_EDIT_TEXT_DATA);
            editTextData.setText(savedText);
            Log.d(TAG, "onRestoreInstanceState() - Khôi phục dữ liệu EditText: " + savedText);
        }
    }
}