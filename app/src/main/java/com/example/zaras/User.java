package com.example.zaras;

public class User {
    private String name;
    private String email;
    private String phone;

    // Required default constructor for DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}