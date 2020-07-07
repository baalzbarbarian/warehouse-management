package com.duan1.Assignment_single_team.mModel;

import java.util.Date;

public class mHoaDon {
    private String mahoadon;
    private Date ngaytaohoadon;
    private String ghichu;
    private String manhanvien;

    public mHoaDon() {
    }

    public mHoaDon(String mahoadon, Date ngaytaohoadon, String ghichu, String manhanvien) {
        this.mahoadon = mahoadon;
        this.ngaytaohoadon = ngaytaohoadon;
        this.ghichu = ghichu;
        this.manhanvien = manhanvien;
    }

    public String getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(String manhanvien) {
        this.manhanvien = manhanvien;
    }

    public Date getNgaytaohoadon() {
        return ngaytaohoadon;
    }

    public void setNgaytaohoadon(Date ngaytaohoadon) {
        this.ngaytaohoadon = ngaytaohoadon;
    }

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
