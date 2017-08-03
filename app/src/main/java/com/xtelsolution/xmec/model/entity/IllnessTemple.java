package com.xtelsolution.xmec.model.entity;

import org.jsoup.nodes.Document;

/**
 * Created by HUNGNT on 3/14/2017.
 */

public class IllnessTemple {
    private String name;
    private String detail_url;

    public IllnessTemple(String name, String detail_url) {
        this.name = name;
        this.detail_url = detail_url;
    }

    public static IllnessTemple fromDocument(Document document) {
        String name = document.select("a.wtc-link-shapo").first().outerHtml();
        String detail = document.select("div.wtc-div-title").first().outerHtml();
        return new IllnessTemple(name, detail);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail_url;
    }

    public void setDetail(String detail) {
        this.detail_url = detail;
    }

}
