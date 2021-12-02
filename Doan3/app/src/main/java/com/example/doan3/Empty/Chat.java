package com.example.doan3.Empty;

public class Chat {
    String nguoigui;
    String avnguoigui;
    String nguoinhan;
    String avnguoinhan;
    String mess;

    public String getNguoigui() {
        return nguoigui;
    }

    public void setNguoigui(String nguoigui) {
        this.nguoigui = nguoigui;
    }

    public String getNguoinhan() {
        return nguoinhan;
    }

    public void setNguoinhan(String nguoinhan) {
        this.nguoinhan = nguoinhan;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getAvnguoigui() {
        return avnguoigui;
    }

    public void setAvnguoigui(String avnguoigui) {
        this.avnguoigui = avnguoigui;
    }

    public String getAvnguoinhan() {
        return avnguoinhan;
    }

    public void setAvnguoinhan(String avnguoinhan) {
        this.avnguoinhan = avnguoinhan;
    }

    public Chat(String nguoigui, String avnguoigui, String nguoinhan, String avnguoinhan, String mess) {
        this.nguoigui = nguoigui;
        this.avnguoigui = avnguoigui;
        this.nguoinhan = nguoinhan;
        this.avnguoinhan = avnguoinhan;
        this.mess = mess;
    }

    public Chat() {
    }
}
