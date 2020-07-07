package com.duan1.Assignment_single_team.mModel;

import java.util.Date;

public class mHDNHChiTiet {
    private String mahdct;
    private String maloaihang;
    private String masp;
    private int soluong;
    private double giasp;
    private String mancc;
    private boolean checkStorage;

    public mHDNHChiTiet() {
    }

    public mHDNHChiTiet(String mahdct, String maloaihang, String masp, int soluong, double giasp, String mancc) {
        this.mahdct = mahdct;
        this.maloaihang = maloaihang;
        this.masp = masp;
        this.soluong = soluong;
        this.giasp = giasp;
        this.mancc = mancc;
    }

    public String getMahdct() {
        return mahdct;
    }

    public void setMahdct(String mahdct) {
        this.mahdct = mahdct;
    }

    public String getMaloaihang() {
        return maloaihang;
    }

    public void setMaloaihang(String maloaihang) {
        this.maloaihang = maloaihang;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public double getGiasp() {
        return giasp;
    }

    public void setGiasp(double giasp) {
        this.giasp = giasp;
    }

    public String getMancc() {
        return mancc;
    }

    public void setMancc(String mancc) {
        this.mancc = mancc;
    }
}
