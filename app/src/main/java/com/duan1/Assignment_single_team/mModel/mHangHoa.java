package com.duan1.Assignment_single_team.mModel;

import androidx.annotation.NonNull;

public class mHangHoa {
    private String mahanghoa;
    private String tenhanghoa;
    private int soluong;
    private double giahanghoa;
    private String maloaihang;
    private String vitri;

    public mHangHoa() {
    }

    public mHangHoa(String mahanghoa, String tenhanghoa, int soluong, double giahanghoa, String maloaihang, String vitri) {
        this.mahanghoa = mahanghoa;
        this.tenhanghoa = tenhanghoa;
        this.soluong = soluong;
        this.giahanghoa = giahanghoa;
        this.maloaihang = maloaihang;
        this.vitri = vitri;
    }

    public String getMahanghoa() {
        return mahanghoa;
    }

    public void setMahanghoa(String mahanghoa) {
        this.mahanghoa = mahanghoa;
    }

    public String getTenhanghoa() {
        return tenhanghoa;
    }

    public void setTenhanghoa(String tenhanghoa) {
        this.tenhanghoa = tenhanghoa;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public double getGiahanghoa() {
        return giahanghoa;
    }

    public void setGiahanghoa(double giahanghoa) {
        this.giahanghoa = giahanghoa;
    }

    public String getMaloaihang() {
        return maloaihang;
    }

    public void setMaloaihang(String maloaihang) {
        this.maloaihang = maloaihang;
    }

    public String getVitri() {
        return vitri;
    }

    public void setVitri(String vitri) {
        this.vitri = vitri;
    }

    @NonNull
    @Override
    public String toString() {
        return getMahanghoa() +" | "+ getTenhanghoa();
    }
}
