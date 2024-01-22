package com.files.management.controller.request;

import jakarta.validation.constraints.NotBlank;

public class LocationRequest {

  @NotBlank(message = "location is required")
  private String location;
  
  @NotBlank(message = "location is required")
  private String shelfNumber;

  public LocationRequest(String location, String shelfNumber) {
    this.location = location;
    this.shelfNumber = shelfNumber;
  }

  public String getLocation() {
    return location;
  }

  public String getShelfNumber() {
    return shelfNumber;
  }
}
