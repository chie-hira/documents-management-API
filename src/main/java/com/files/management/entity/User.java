package com.files.management.entity;

import java.time.LocalDateTime;
import java.util.Objects;

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

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setIsAdmin(boolean admin) {
    isAdmin = admin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return id == user.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, password, isAdmin, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    return """
        User{
        id=%d,
        name='%s',
        email='%s',
        password='%s',
        isAdmin=%b,
        createdAt=%s,
        updatedAt=%s
        }
        """.formatted(id, name, email, password, isAdmin, createdAt, updatedAt);
  }
}
