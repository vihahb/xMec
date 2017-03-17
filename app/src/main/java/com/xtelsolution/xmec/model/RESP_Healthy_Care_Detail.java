package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;

/**
 * Created by phimau on 3/10/2017.
 */

public class RESP_Healthy_Care_Detail extends RESP_Map_Healthy_Care {
    @Expose
    private String openTime;
    @Expose
    private String address;
    @Expose
    private int voteRate;
    @Expose
    private String numPhone;
    @Expose
    private String fax;
    @Expose
    private String introduce;
    @Expose
    private String urlAvatar;

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getNumPhone() {
        return numPhone;
    }

    public void setNumPhone(String numPhone) {
        this.numPhone = numPhone;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public int getVoteRate() {
        return voteRate;
    }

    public void setVoteRate(int voteRate) {
        this.voteRate = voteRate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

