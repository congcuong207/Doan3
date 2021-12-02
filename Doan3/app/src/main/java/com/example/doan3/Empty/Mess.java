package com.example.doan3.Empty;

public class Mess {
    int avatar;
    String mess;
    int trai;

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public int getTrai() {
        return trai;
    }

    public void setTrai(int trai) {
        this.trai = trai;
    }

    public Mess(int avatar, String mess, int trai) {
        this.avatar = avatar;
        this.mess = mess;
        this.trai = trai;
    }
}
