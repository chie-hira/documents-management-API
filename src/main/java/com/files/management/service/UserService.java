package com.files.management.service;

import com.files.management.entity.User;
import com.files.management.exception.DuplicateException;
import com.files.management.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class UserService {

  private final UserMapper userMapper;

  public UserService(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public User insert(String name, String email, String password, boolean isAdmin) {
    if (userMapper.isNotUserUnique(name, email)) {
      throw new DuplicateException("すでに登録済みのユーザーです");
    }
    User user = new User(name, email, password, isAdmin);
    userMapper.insert(user);
    return user;
  }

}
