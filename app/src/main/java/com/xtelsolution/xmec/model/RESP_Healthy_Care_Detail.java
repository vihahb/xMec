package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;

/**
 * Created by phimau on 3/10/2017.
 */

public class RESP_Healthy_Care_Detail extends RESP_Map_Healthy_Care {
    @Expose
    private String open_time;
    @Expose
    private int vote_rate;
    @Expose
    private String num_phone;
    @Expose
    private String fax;
    @Expose
    private String introduce;
    @Expose
    private String url_avatar;


    public String getOpenTime() {
        return open_time;
    }

    public void setOpenTime(String openTime) {
        this.open_time = openTime;
    }

    public int getVote_rate() {
        return vote_rate;
    }

    public void setVote_rate(int vote_rate) {
        this.vote_rate = vote_rate;
    }

    public String getNum_phone() {
        return num_phone;
    }

    public void setNum_phone(String num_phone) {
        this.num_phone = num_phone;
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

    public String getUrl_avatar() {
        return url_avatar;
    }

    public void setUrl_avatar(String url_avatar) {
        this.url_avatar = url_avatar;
    }
}

