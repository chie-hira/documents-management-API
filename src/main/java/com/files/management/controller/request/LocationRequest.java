package com.files.management.controller.request;

import com.files.management.controller.response.LocationResponse;

public class LocationRequest {
    private String location;
    private int shelfNumber;
    public LocationRequest(String location, int shelfNumber) {
        this.location = location;
        this.shelfNumber = shelfNumber;
    }

    public String getLocation(){
        return location;
    }
    public int getShelfNumber(){
        return shelfNumber;
    }
}
