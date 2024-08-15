package com.files.management.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.files.management.entity.User;
import com.files.management.exception.DuplicateException;
import com.files.management.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  UserService userService;

  @Mock
  UserMapper userMapper;

  @Test
  public void ユーザーを登録できること() {
    // テスト対象を実行
    User user = new User("山田太郎", "taro@email.com", "password", false);
    User insertUser = userService.insert(user);

    // 検証
    assertNotNull(insertUser);
    assertThat(insertUser).isEqualTo(new User("山田太郎", "taro@email.com", "password", false));

    verify(userMapper, times(1)).insert(insertUser);

  }

  @Test
  public void 既に登録済みのユーザーを登録しようとしたとき例外が投げられること() {
    // モックと検証のデータを用意
    String existingUserName = "name1";
    String existingUserEmail = "name1@email.com";
    String exceptionMessage = "すでに登録済みのユーザーです";

    // モックの動作を定義
    when(userMapper.isNotUserUnique(existingUserName, existingUserEmail)).thenReturn(true);

    // 検証　テスト対象を実行するとexceptionが発生するのでいきなり検証
    User user = new User(existingUserName, existingUserEmail, "password", false);
    assertThatThrownBy(
        () -> userService.insert(user))
        .isInstanceOf(DuplicateException.class)
        .hasMessage(exceptionMessage);
    verify(userMapper, times(1)).isNotUserUnique(existingUserName, existingUserEmail);
    verify(userMapper, times(0)).insert(any(User.class));
  }

}
