package com.example.realunimarket;
public class UserInfo {

    public  String email;
    public  String password;
    public String name;
    public  String major;

    public UserInfo(){

    }

    public UserInfo(String email,String password,String name,String major) {
        this.email  = email; this.password = password; this.name=name; this.major = major;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setName(String name) {
        this.name = name;
    }
}
