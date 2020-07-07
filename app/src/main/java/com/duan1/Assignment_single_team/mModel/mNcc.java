package com.duan1.Assignment_single_team.mModel;

import androidx.annotation.NonNull;

public class mNcc {
    private String mancc;
    private String tenncc;
    private String diachi;
    private String phonencc;
    private String mahangncc;
    private byte[] logoncc;

    public mNcc() {
    }

    public mNcc(String mancc, String tenncc, String diachi, String phonencc, String mahangncc, byte[] logoncc) {
        this.mancc = mancc;
        this.tenncc = tenncc;
        this.diachi = diachi;
        this.phonencc = phonencc;
        this.mahangncc = mahangncc;
        this.logoncc = logoncc;
    }

    public byte[] getLogoncc() {
        return logoncc;
    }

    public void setLogoncc(byte[] logoncc) {
        this.logoncc = logoncc;
    }

    public String getMancc() {
        return mancc;
    }

    public void setMancc(String mancc) {
        this.mancc = mancc;
    }

    public String getTenncc() {
        return tenncc;
    }

    public void setTenncc(String tenncc) {
        this.tenncc = tenncc;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getPhonencc() {
        return phonencc;
    }

    public void setPhonencc(String phonencc) {
        this.phonencc = phonencc;
    }

    public String getMahangncc() {
        return mahangncc;
    }

    public void setMahangncc(String mahangncc) {
        this.mahangncc = mahangncc;
    }

    @NonNull
    @Override
    public String toString() {
        return getMancc()+" | "+getTenncc();
    }
}
