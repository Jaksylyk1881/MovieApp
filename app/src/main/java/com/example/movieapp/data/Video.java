package com.example.movieapp.data;

public class Video {
    private String name;
    private String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Video(String name, String link) {
        this.name = name;
        this.link = link;
    }
}
