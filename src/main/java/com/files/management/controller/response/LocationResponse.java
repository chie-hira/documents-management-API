package com.files.management.controller.response;

public class LocationResponse {
    private String message;
    private int id;
    private String location;
    private String shelfNumber;

    public LocationResponse(String message, int id, String location, String shelfNumber) {
        this.message = message;
        this.id = id;
        this.location = location;
        this.shelfNumber = shelfNumber;
    }

    public void setDefaultId(int defaultId) {
        if (this.id == 0) {
            this.id = defaultId;
        }
    }

    public void setDefaultLocation(String defaultLocation) {
        if (this.location == null) {
            this.location = defaultLocation;
        }
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getShelfNumber() {
        return shelfNumber;
    }

    public String getMessage(){
        return message;
    }
}
