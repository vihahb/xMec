package com.xtelsolution.xmec.model;

/**
 * Created by ChungDT on 2/2/2017.
 */

public class Article {
    private int id;
    private String parent_avatar;
    private String parent_name;
    private String title;
    private int type;
    private String type_name;
    private long time_create;
    private int total_like;
    private int total_view;
    private int total_comment;

    public Article() {
    }

    public Article(int id, String parent_avatar, String parent_name, String title, int type, String type_name, long time_create, int total_like, int total_view, int total_comment) {
        this.id = id;
        this.parent_avatar = parent_avatar;
        this.parent_name = parent_name;
        this.title = title;
        this.type = type;
        this.type_name = type_name;
        this.time_create = time_create;
        this.total_like = total_like;
        this.total_view = total_view;
        this.total_comment = total_comment;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", parent_avatar='" + parent_avatar + '\'' +
                ", parent_name='" + parent_name + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", type_name='" + type_name + '\'' +
                ", time_create=" + time_create +
                ", total_like=" + total_like +
                ", total_view=" + total_view +
                ", total_comment=" + total_comment +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParent_avatar() {
        return parent_avatar;
    }

    public void setParent_avatar(String parent_avatar) {
        this.parent_avatar = parent_avatar;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public long getTime_create() {
        return time_create;
    }

    public void setTime_create(long time_create) {
        this.time_create = time_create;
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
