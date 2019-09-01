package com.gigaworks.tech.bible.container;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private String title, category;
    private int id;
    private List<Chapter> chapters;

    public Book() {
    }

    public Book(String title, String category, int id, List<Chapter> chapters) {
        this.title = title;
        this.category = category;
        this.id = id;
        this.chapters = chapters;
    }

    public Book(String title, String category, int id) {
        this.title = title;
        this.category = category;
        this.id = id;
        this.chapters = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public void insertChapter(Chapter chapter) {
        this.chapters.add(chapter);
    }
}
