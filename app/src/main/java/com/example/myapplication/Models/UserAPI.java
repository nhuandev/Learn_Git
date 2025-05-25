package com.example.myapplication.Models;

import java.util.List;

public class UserAPI {
     String message;
     int status;
    List<User> list;

    public UserAPI(String message, int status, List<User> list) {
        this.message = message;
        this.status = status;
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}
