package com.files.management.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FileCategoryRequest {

  @NotNull
  private int id;
  @NotBlank(message = "privacyType is required")
  private String privacyType;

  @NotBlank(message = "storageYear is required")
  private int storageYear;

  public FileCategoryRequest(String privacyType, int storageYear) {
    this.privacyType = privacyType;
    this.storageYear = storageYear;
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
