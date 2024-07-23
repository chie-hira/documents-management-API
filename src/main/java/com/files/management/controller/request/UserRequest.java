package com.files.management.controller.request;

import com.files.management.validator.UniqueEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserRequest {

  @NotNull
  private int id;
  @NotBlank(message = "名前は必須です")
  private String name;
  @NotBlank(message = "メールアドレスは必須です")
  @UniqueEmail
  private String email;
  @NotBlank(message = "パスワードは必須です")
  private String password;
  private boolean isAdmin;

  public UserRequest(String name, String email, String password, boolean isAdmin) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.isAdmin = isAdmin;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public boolean getIsAdmin() {
    return isAdmin;
  }

}
