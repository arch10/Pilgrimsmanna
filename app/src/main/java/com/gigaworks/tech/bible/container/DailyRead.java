package com.gigaworks.tech.bible.container;

public class DailyRead {

    private String title, url, category;
    private int id;

    public DailyRead() {}

    public DailyRead(String title, int id, String url, String category) {
        this.title = title;
        this.id = id;
        this.url = url;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
