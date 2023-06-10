package com.example.lostfoundapp;

public class DatabaseItem {
    private String name;
    private String phone;
    private String description;
    private String date;
    private String location;
    private String status;
    private String ltdLng;

    public DatabaseItem(String name, String phone, String description, String date, String location, String status, String ltdLng) {
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
        this.status = status;
        this.ltdLng = ltdLng;
    }

    public String getStatus() {
        return status;
    }

    public String getLtdLng() {
        return ltdLng;
    }

    public void setLtdLng(String ltdLng) {
        this.ltdLng = ltdLng;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
