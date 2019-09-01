package com.gigaworks.tech.bible.container;

public class Chapter {

    private int id, chapterNo;
    private String text, soundfile;
    private Boolean blocked;

    public Chapter(int id, int chapterNo, String text, String soundfile, Boolean blocked) {
        this.id = id;
        this.chapterNo = chapterNo;
        this.text = text;
        this.soundfile = soundfile;
        this.blocked = blocked;
    }

    public Chapter(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSoundfile() {
        return soundfile;
    }

    public void setSoundfile(String soundfile) {
        this.soundfile = soundfile;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
}
