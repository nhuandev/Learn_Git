package com.example.myapplication.Fragment;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Activitys.UserActivity;
import com.example.myapplication.R;


public class ProfileFragment extends Fragment {
    private static final String TAG = "MyFragmentLifecycle"; // TAG cho Logcat
    private static final String KEY_FRAGMENT_EDIT_TEXT = "fragmentEditText";

    private Button btnUser;
    private EditText fragmentEditText;
    private String savedEditTextData = "";

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    // Phương thức static để tạo instance mới của Fragment
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        // Có thể thêm args nếu cần truyền dữ liệu khi tạo Fragment
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called");
        // Ở đây Fragment đã được gắn vào Activity
        // Bạn có thể lấy tham chiếu đến Activity nếu cần
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        // Khởi tạo các thành phần không liên quan đến UI
        // Khôi phục trạng thái đã lưu
        if (savedInstanceState != null) {
            savedEditTextData = savedInstanceState.getString(KEY_FRAGMENT_EDIT_TEXT, "");
            Log.d(TAG, "onCreate() - Khôi phục dữ liệu: " + savedEditTextData);
        }

        // Nếu bạn muốn Fragment được giữ lại khi thay đổi cấu hình (ví dụ: xoay màn hình)
        // setRetainInstance(true); // Ghi chú: Cách này bị deprecated và ViewModel là cách ưu tiên hơn
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called");
        // Tạo và trả về View gốc của Fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated() called");
        // Ánh xạ các View con và thiết lập listener
        btnUser = view.findViewById(R.id.btnUser);
        fragmentEditText = view.findViewById(R.id.fragment_edit_text);

        // Đặt dữ liệu đã khôi phục vào EditText
        if (!savedEditTextData.isEmpty()) {
            fragmentEditText.setText(savedEditTextData);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored() called");
    }
    //    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Log.d(TAG, "onActivityCreated() called");
//        // Ghi chú: Callback này bị deprecated.
//        // Hầu hết các thao tác ở đây nên chuyển sang onViewCreated() hoặc ViewModel.
//    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
        // Fragment hiển thị với người dùng
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        // Fragment sẵn sàng tương tác với người dùng
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
        // Fragment sắp mất trọng tâm
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
        // Fragment không còn hiển thị
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called");
        // View của Fragment bị hủy. Giải phóng tài nguyên liên quan đến View.
        fragmentEditText = null; // Tránh rò rỉ bộ nhớ
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
        // Fragment bị hủy. Giải phóng tài nguyên không liên quan đến View.
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach() called");
        // Fragment bị tách khỏi Activity
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState() called");
        // Lưu trạng thái của EditText
        outState.putString(KEY_FRAGMENT_EDIT_TEXT, fragmentEditText.getText().toString());
    }
}
