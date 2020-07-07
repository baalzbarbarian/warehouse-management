package com.duan1.Assignment_single_team.mModel;

public class mCheckStorage {
    private String maHD;
    private int check;

    public mCheckStorage() {
    }

    public mCheckStorage(String maHD, int check) {
        this.maHD = maHD;
        this.check = check;
    }

    public String getMaHD() {
        return maHD;
    }

    public int getCheck() {
        return check;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public void setCheck(int check) {
        this.check = check;
    }
}
