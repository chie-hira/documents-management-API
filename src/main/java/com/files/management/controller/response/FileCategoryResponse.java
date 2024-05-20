package com.files.management.controller.response;

public class FileCategoryResponse {

  private String message;
  private int id;
  private String privacyType;
  private int storageYear;

  public FileCategoryResponse(String message, int id, String privacyType, int storageYear) {
    this.message = message;
    this.id = id;
    this.privacyType = privacyType;
    this.storageYear = storageYear;
  }

  public String getMessage() {
    return message;
  }

  public int getId() {
    return id;
  }

  public String getPrivacyType() {
    return privacyType;
  }

  public int getStorageYear() {
    return storageYear;
  }
}
