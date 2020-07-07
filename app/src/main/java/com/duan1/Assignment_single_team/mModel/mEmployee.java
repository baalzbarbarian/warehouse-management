package com.duan1.Assignment_single_team.mModel;

public class mEmployee {
    private String username;
    private String password;
    private String name;
    private String phonenumber;
    private String address;

    public mEmployee() {
    }

    public mEmployee(String username, String password, String name, String phonenumber, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
