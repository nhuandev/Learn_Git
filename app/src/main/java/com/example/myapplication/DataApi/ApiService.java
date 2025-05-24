package com.example.myapplication.DataApi;

import com.example.myapplication.Models.Post;
import com.example.myapplication.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();

    @GET("posts")
    Call<List<Post>> getPosts();
}
