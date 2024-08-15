package com.files.management.controller.response;

public class UserResponse {

  private String message;
  private int id;
  private String name;
  private String email;
  private boolean isAdmin;

  public UserResponse(String message, int id, String name, String email, boolean isAdmin) {
    this.message = message;
    this.id = id;
    this.name = name;
    this.email = email;
    this.isAdmin = isAdmin;
  }

  public String getMessage() {
    return message;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public boolean getIsAdmin() {
    return isAdmin;
  }
}
