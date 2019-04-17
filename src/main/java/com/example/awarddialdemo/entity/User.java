package com.example.awarddialdemo.entity;


/**
 * @Author: HanLong
 * @Date: Create in 2018/3/8 21:40
 * @Description:
 */
public class User {

    private String userName;

    private String password;

    public User() {

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
