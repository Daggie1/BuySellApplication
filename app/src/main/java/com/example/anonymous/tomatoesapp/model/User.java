package com.example.anonymous.tomatoesapp.model;


public class User {
    String phone,email,password,uuid;

    public User(String phone, String email, String password, String uuid) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.uuid = uuid;
    }

    public User() {
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUuid() {
        return uuid;
    }
}
