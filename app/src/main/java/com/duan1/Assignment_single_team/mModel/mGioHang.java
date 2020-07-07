package com.duan1.Assignment_single_team.mModel;

public class mGioHang {

    private int magiohang;
    private String mahdxh;
    private String mahang;
    private double giahang;
    private int soluong;

    public mGioHang() {
    }

    public mGioHang(String mahdxh, String mahang, double giahang, int soluong) {
        this.mahdxh = mahdxh;
        this.mahang = mahang;
        this.giahang = giahang;
        this.soluong = soluong;
    }

    public int getMagiohang() {
        return magiohang;
    }

    public String getMahdxh() {
        return mahdxh;
    }

    public void setMahdxh(String mahdxh) {
        this.mahdxh = mahdxh;
    }

    public String getMahang() {
        return mahang;
    }

    public void setMahang(String mahang) {
        this.mahang = mahang;
    }

    public double getGiahang() {
        return giahang;
    }

    public void setGiahang(double giahang) {
        this.giahang = giahang;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
