package com.files.management.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FileCategoryRequest {

  @NotNull
  private int id;
  @NotBlank(message = "privacyType is required")
  private String privacyType;

  @NotNull
  private Integer storageYear;

  public FileCategoryRequest(String privacyType, Integer storageYear) {
    this.privacyType = privacyType;
    this.storageYear = storageYear;
  }

  public int getId() {
    return id;
  }

  public String getPrivacyType() {
    return privacyType;
  }

  public Integer getStorageYear() {
    return storageYear;
  }
}
