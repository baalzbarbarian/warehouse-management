package com.duan1.Assignment_single_team.mModel;

public class mTKDTTheoThang {
    private String thangtrongnam;
    private int doanhthuthang;

    public mTKDTTheoThang() {
    }

    public mTKDTTheoThang(String thangtrongnam, int doanhthuthang) {
        this.thangtrongnam = thangtrongnam;
        this.doanhthuthang = doanhthuthang;
    }

    public String getThangtrongnam() {
        return thangtrongnam;
    }

    public void setThangtrongnam(String thangtrongnam) {
        this.thangtrongnam = thangtrongnam;
    }

    public long getDoanhthuthang() {
        return doanhthuthang;
    }

    public void setDoanhthuthang(int doanhthuthang) {
        this.doanhthuthang = doanhthuthang;
    }
}
