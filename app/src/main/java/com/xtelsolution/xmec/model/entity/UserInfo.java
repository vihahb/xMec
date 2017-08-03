package com.xtelsolution.xmec.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by vivhp on 7/31/2017.
 */

public class UserInfo implements Serializable {

    @Expose
    private int uid;
    @Expose
    private String fullname;
    @Expose
    private int gender;
    @Expose
    private long birthday;
    @Expose
    private String phonenumber;
    @Expose
    private String address;
    @Expose
    private String avatar;
    @Expose
    private double weight;
    @Expose
    private double height;

    private boolean isHeader = false;

    private boolean isAccepted = false;

    public UserInfo() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid=" + uid +
                ", fullname='" + fullname + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", phonenumber='" + phonenumber + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", isHeader=" + isHeader +
                '}';
    }
}
