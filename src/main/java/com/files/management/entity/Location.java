package com.files.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

//@Entity
@Table(name = "locations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"location", "shelf_number"})
})
public class Location {
    private int id;
    @Column(name = "location")
    private String location;
    @Column(name = "shelf_number")
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
