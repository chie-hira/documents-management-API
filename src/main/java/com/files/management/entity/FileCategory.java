package com.files.management.entity;

import java.time.LocalDateTime;

public class FileCategory {

  private int id;
  private String privacyType;
  private int storageYear;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public FileCategory() {
  }

  public FileCategory(int id, String privacyType, int storageYear, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.privacyType = privacyType;
    this.storageYear = storageYear;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public FileCategory(String privacyType, int storageYear) {
    this.privacyType = privacyType;
    this.storageYear = storageYear;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPrivacyType() {
    return privacyType;
  }

  public void setPrivacyType(String privacyType) {
    this.privacyType = privacyType;
  }

  public int getStorageYear() {
    return storageYear;
  }

  public void setStorageYear(int storageYear) {
    this.storageYear = storageYear;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
