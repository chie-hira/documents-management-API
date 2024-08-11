package com.files.management.controller.request;

import com.files.management.entity.User;
import com.files.management.validator.UniqueEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter // Getterメソッドを自動生成
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

  public User convertToEntity() {
    User user = new User();
    user.setName(this.name);
    user.setEmail(this.email);
    user.setPassword(this.password);
    user.setIsAdmin(this.isAdmin);
    return user;
  }

}
