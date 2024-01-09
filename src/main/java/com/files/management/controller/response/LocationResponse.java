package com.files.management.controller.response;

public class LocationResponse {
    private String message;

    public LocationResponse(String message){
        this.message =message;
    }

    public String getMessage(){
        return message;
    }
}
