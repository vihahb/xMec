package com.xtelsolution.xmec.model.entity;

import java.io.Serializable;

/**
 * Created by ChungDT on 2/2/2017.
 */

public class Article implements Serializable {
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_OTHER = 2;
    private int id;
    private NewsAuthor author;
    private NewsFeed newsFeed;
    private int type;
    private String type_name;
    private String total_like;
    private String total_view;
    private String total_comment;

    public Article() {
    }

    public Article(int id, NewsAuthor author, NewsFeed newsFeed, int type, String type_name, String total_like, String total_view, String total_comment) {
        this.id = id;
        this.author = author;
        this.newsFeed = newsFeed;
        this.type = type;
        this.type_name = type_name;
        this.total_like = total_like;
        this.total_view = total_view;
        this.total_comment = total_comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NewsAuthor getAuthor() {
        return author;
    }

    public void setAuthor(NewsAuthor author) {
        this.author = author;
    }

    public NewsFeed getNewsFeed() {
        return newsFeed;
    }

    public void setNewsFeed(NewsFeed newsFeed) {
        this.newsFeed = newsFeed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getTotal_like() {
        return total_like;
    }

    public void setTotal_like(String total_like) {
        this.total_like = total_like;
    }

    public String getTotal_view() {
        return total_view;
    }

    public void setTotal_view(String total_view) {
        this.total_view = total_view;
    }

    public String getTotal_comment() {
        return total_comment;
    }

    public void setTotal_comment(String total_comment) {
        this.total_comment = total_comment;
    }
}
