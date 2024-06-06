package com.files.management.controller.request;

import com.files.management.validator.ValidPrivacyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FileCategoryRequest {

  @NotNull
  private int id;
  @NotBlank(message = "開示区分は必須です")
  @ValidPrivacyType
  private String privacyType;

  @NotNull(message = "保存年数は必須です")
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
