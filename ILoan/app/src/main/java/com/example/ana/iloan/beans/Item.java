package com.example.ana.iloan.beans;

public class Item {
    private int id;
    private String title;
    private String kind;
    private String genre;
    private String image;
    private String year;

    public Item(){

    }
    public Item(String title, String kind, String genre, String image, String year){
        this.title = title;
        this.kind = kind;
        this.genre = genre;
        this.image = image;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
