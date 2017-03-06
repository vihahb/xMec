package com.xtelsolution.xmec.model.entity;

/**
 * Created by HUNGNT on 3/2/2017.
 */

public class NewsAuthor {
    private String name;
    private String avatar_url;
    private String website_url;

    public NewsAuthor(String name, String avatar_url, String website_url) {
        this.name = name;
        this.avatar_url = avatar_url;
        this.website_url = website_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(String website_url) {
        this.website_url = website_url;
    }
}
