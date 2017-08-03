package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtelsolution.xmec.common.Constant;

/**
 * Created by phimau on 2/15/2017.
 */

public class RESP_User extends RESP_Basic {
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
    private boolean isMaster = true;

    public RESP_User(String fullname, int gender, long birthday, String phonenumber, String address, String avatar, double weight, double height) {
        this.fullname = fullname;
        this.gender = gender;
        this.birthday = birthday;
        this.phonenumber = phonenumber;
        this.address = address;
        this.avatar = avatar;
        this.weight = weight;
        this.height = height;
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

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getBirthDayasString() {
        return Constant.getDate(this.birthday);
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
    }

    @Override
    public String toString() {
        return "RESP_User{" +
                "address='" + address + '\'' +
                ", fullname='" + fullname + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", phonenumber='" + phonenumber + '\'' +
                ", avatar='" + avatar + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                '}';
    }

}
