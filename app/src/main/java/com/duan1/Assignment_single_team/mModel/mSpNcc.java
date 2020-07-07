package com.duan1.Assignment_single_team.mModel;

import androidx.annotation.NonNull;

public class mSpNcc {
    private String masp;
    private String tensp;
    private double giasp;
    private String maloaisp;
    private String mancc;
    private byte[] logospncc;

    public mSpNcc() {
    }

    public String getMancc() {
        return mancc;
    }

    public byte[] getLogospncc() {
        return logospncc;
    }

    public void setLogospncc(byte[] logospncc) {
        this.logospncc = logospncc;
    }

    public void setMancc(String mancc) {
        this.mancc = mancc;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public double getGiasp() {
        return giasp;
    }

    public void setGiasp(double giasp) {
        this.giasp = giasp;
    }

    public String getMaloaisp() {
        return maloaisp;
    }

    public void setMaloaisp(String maloaisp) {
        this.maloaisp = maloaisp;
    }

    public mSpNcc(String masp, String tensp, double giasp, String maloaisp, String mancc, byte[] logospncc) {
        this.masp = masp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.maloaisp = maloaisp;
        this.mancc = mancc;
        this.logospncc = logospncc;
    }

    @NonNull
    @Override
    public String toString() {
        return getMasp() +" | "+getTensp();
    }
}
