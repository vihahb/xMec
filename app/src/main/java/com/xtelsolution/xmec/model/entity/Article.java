package com.xtelsolution.xmec.model.entity;

/**
 * Created by ChungDT on 2/2/2017.
 */

public class Article {
    private int id;
    private NewsAuthor author;
    private NewsFeed newsFeed;
    private int type;
    private String type_name;
    private int total_like;
    private int total_view;
    private int total_comment;

    public Article() {
    }

    public Article(int id, NewsAuthor author, NewsFeed newsFeed, int type, String type_name, int total_like, int total_view, int total_comment) {
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

    public int getTotal_like() {
        return total_like;
    }

    public void setTotal_like(int total_like) {
        this.total_like = total_like;
    }

    public int getTotal_view() {
        return total_view;
    }

    public void setTotal_view(int total_view) {
        this.total_view = total_view;
    }

    public int getTotal_comment() {
        return total_comment;
    }

    public void setTotal_comment(int total_comment) {
        this.total_comment = total_comment;
    }
}
