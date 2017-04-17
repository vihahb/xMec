package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;

/**
 * Created by phimau on 3/10/2017.
 */

public class RESP_Healthy_Care_Detail extends RESP_Map_Healthy_Care {
    @Expose
    private int rating;
    @Expose
    private int total_doctor;
    @Expose
    private String tag;
    @Expose
    private String url;

    public int getVote_rate() {
        return rating;
    }

    public void setVote_rate(int vote_rate) {
        this.rating = vote_rate;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getTotal_doctor() {
        return total_doctor;
    }

    public void setTotal_doctor(int total_doctor) {
        this.total_doctor = total_doctor;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

