package com.files.management.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class LocationRequest {
    @NotBlank(message ="location is required")
    private String location;
    @Positive(message = "shelfNumber is required")
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
