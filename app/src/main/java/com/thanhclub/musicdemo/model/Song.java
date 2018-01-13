package com.thanhclub.musicdemo.model;

import android.support.annotation.NonNull;

public class Song implements Comparable<Song> {
    private String id;
    private String title;
    private String singer;
    private String data;
    private int duration;

    public Song(String id, String title, String singer, String data, int duration) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.data = data;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int compareTo(@NonNull Song song) {
        return this.title.compareTo(song.getTitle());
    }
}
