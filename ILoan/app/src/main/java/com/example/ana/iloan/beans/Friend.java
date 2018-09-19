package com.example.ana.iloan.beans;

public class Friend {
    private int id;
    private String name;
    private String surname;
    private String phone;
    private String image;

    public Friend(String name, String surname, String phone, String image) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.image = image;
    }

    public Friend() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}