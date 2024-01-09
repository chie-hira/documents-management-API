package com.files.management.entity;

public class Location {
    private int id;
    private String location;
    private int shelfNumber;

    public Location(int id, String location, int shelfNumber) {
        this.id = id;
        this.location = location;
        this.shelfNumber = shelfNumber;
    }

    public Location(String location, int shelfNumber) {
        this.location = location;
        this.shelfNumber = shelfNumber;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }
}
