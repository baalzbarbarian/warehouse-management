package com.duan1.Assignment_single_team.mModel;

public class mLoaiHang {
    private String maloaihang;
    private String tenloaihang;
    private String vitri;

    public mLoaiHang() {
    }

    public mLoaiHang(String maloaihang, String tenloaihang, String vitri) {
        this.maloaihang = maloaihang;
        this.tenloaihang = tenloaihang;
        this.vitri = vitri;
    }

    public String getVitri() {
        return vitri;
    }

    public void setVitri(String vitri) {
        this.vitri = vitri;
    }

    public String getMaloaihang() {
        return maloaihang;
    }

    public void setMaloaihang(String maloaihang) {
        this.maloaihang = maloaihang;
    }

    public String getTenloaihang() {
        return tenloaihang;
    }

    public void setTenloaihang(String tenloaihang) {
        this.tenloaihang = tenloaihang;
    }
    @Override
    public String toString() {
        return getMaloaihang()+" | "+getTenloaihang();
    }
}
