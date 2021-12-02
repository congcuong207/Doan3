package com.example.doan3.Empty;

public class User {
    String id;
    String name;
    int avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public User(String id, String name, int avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }
}
