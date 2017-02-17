package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtelsolution.xmec.common.Constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by phimau on 2/15/2017.
 */

public class RESP_User extends RESP_Basic {
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
    private Double weight;
    @Expose
    private Double height;

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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
    this.weight = weight;
}

    public Double getHeight() {
        return height;
    }

    public String getBirthDayasString(){
        return Constant.getDate(this.birthday);
    }

    public void setHeight(Double height) {
        this.height = height;
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
