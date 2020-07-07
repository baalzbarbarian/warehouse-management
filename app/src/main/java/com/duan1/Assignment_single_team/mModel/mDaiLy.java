package com.duan1.Assignment_single_team.mModel;

import androidx.annotation.NonNull;

public class mDaiLy {
    private String madaily;
    private String tendaily;
    private String diachi;
    private String phone;

    public mDaiLy() {
    }

    public mDaiLy(String madaily, String tendaily, String diachi, String phone) {
        this.madaily = madaily;
        this.tendaily = tendaily;
        this.diachi = diachi;
        this.phone = phone;
    }

    public String getMadaily() {
        return madaily;
    }

    public void setMadaily(String madaily) {
        this.madaily = madaily;
    }

    public String getTendaily() {
        return tendaily;
    }

    public void setTendaily(String tendaily) {
        this.tendaily = tendaily;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        return getMadaily()+" | "+getTendaily();
    }
}
