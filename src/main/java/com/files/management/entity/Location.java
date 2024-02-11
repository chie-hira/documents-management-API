package com.files.management.entity;

import java.time.LocalDateTime;

public class Location {

  private int id;
  private String location;
  private String shelfNumber;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Location() {
  }

  public Location(int id, String location, String shelfNumber) {
    this.id = id;
    this.location = location;
    this.shelfNumber = shelfNumber;
  }

  public Location(String location, String shelfNumber) {
    this.location = location;
    this.shelfNumber = shelfNumber;
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
