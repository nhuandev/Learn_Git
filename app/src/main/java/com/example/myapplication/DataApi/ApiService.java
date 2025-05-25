package com.example.myapplication.DataApi;

import com.example.myapplication.Models.Post;
import com.example.myapplication.Models.User;
import com.example.myapplication.Models.UserAPI;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();

    @GET("users")
    Observable<UserAPI> getUsersObservable();

    @GET("posts")
    Call<List<Post>> getPosts();
}
