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

  public User insert(User user) {
    if (userMapper.isNotUserUnique(user.getName(), user.getEmail())) {
      throw new DuplicateException("すでに登録済みのユーザーです");
    }
    userMapper.insert(user);
    return user;
  }
}
