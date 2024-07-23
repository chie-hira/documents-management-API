package com.files.management.entity;

import java.time.LocalDateTime;

public class User {

  private int id;
  private String name;
  private String email;
  private String password;
  private boolean isAdmin;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public User() {
  }

  public User(int id, String name, String email, String password, boolean isAdmin,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.isAdmin = isAdmin;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public User(String name, String email, String password, boolean isAdmin) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.isAdmin = isAdmin;
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

  public String getPassword() {
    return password;
  }

  public boolean getIsAdmin() {
    return isAdmin;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
