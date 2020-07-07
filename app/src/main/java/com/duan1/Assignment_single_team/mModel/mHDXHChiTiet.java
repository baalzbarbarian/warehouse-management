package com.duan1.Assignment_single_team.mModel;

import java.util.Date;

public class mHDXHChiTiet {
    private String mahoadon;
    private Date ngaytaohoadon;
    private String ghichu;
    private String manhanvien;
    private String madaily;

    public mHDXHChiTiet() {
    }

    public mHDXHChiTiet(String mahoadon, Date ngaytaohoadon, String ghichu, String manhanvien, String madaily) {
        this.mahoadon = mahoadon;
        this.ngaytaohoadon = ngaytaohoadon;
        this.ghichu = ghichu;
        this.manhanvien = manhanvien;
        this.madaily = madaily;
    }


    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public Date getNgaytaohoadon() {
        return ngaytaohoadon;
    }

    public void setNgaytaohoadon(Date ngaytaohoadon) {
        this.ngaytaohoadon = ngaytaohoadon;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(String manhanvien) {
        this.manhanvien = manhanvien;
    }

    public String getMadaily() {
        return madaily;
    }

    public void setMadaily(String madaily) {
        this.madaily = madaily;
    }
}
