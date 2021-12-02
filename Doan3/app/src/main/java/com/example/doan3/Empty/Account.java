package com.example.doan3.Empty;

public class Account {
    String taikhoan;
    String matkhau;
    String hoten;
    String gioitinh;
    String ngaysinh;
    String sdt;
    String avatar;
    int kt;

    public int getKt() {
        return kt;
    }

    public void setKt(int kt) {
        this.kt = kt;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

//    public Account(String taikhoan, String hoten, String gioitinh, String ngaysinh, String sdt, String matkhau) {
//        this.taikhoan = taikhoan;
//        this.matkhau = matkhau;
//        this.hoten = hoten;
//        this.gioitinh = gioitinh;
//        this.ngaysinh = ngaysinh;
//        this.sdt = sdt;
//        this.avatar = "https://thelifetank.com/wp-content/uploads/2018/08/avatar-default-icon.png";
//    }

    public Account(String taikhoan, String hoten, String gioitinh, String ngaysinh, String sdt, String matkhau, String avatar,int kt) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.avatar = avatar;
        this.kt=kt;
    }

    public Account() {
    }
}
