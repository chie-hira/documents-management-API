package com.files.management.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.files.management.entity.User;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

  @Autowired
  UserMapper userMapper;

  @Test
  @DataSet(value = "datasets/insert_users.yml, datasets/insert_files.yml")
  @Transactional
  void ユーザを登録できること() {
    User user = new User("test", "test@mail.com", "password", false);
    userMapper.insert(user);
    assertThat(user.getId()).isGreaterThan(3);
  }

  @Test
  @DataSet(value = "datasets/insert_users.yml, datasets/insert_files.yml")
  @Transactional
  void 既に登録済みのユーザーを登録しようとしたとき例外が投げられること() {
    assertThat(userMapper.isNotUserUnique("name1", "name1@email.com")).isTrue();
  }

  @Test
  @DataSet(value = "datasets/insert_users.yml, datasets/insert_files.yml")
  @Transactional
  void 既に登録済みのメールアドレスを登録しようとしたとき例外が投げられること() {
    assertThat(userMapper.existsByEmail("name1@email.com")).isTrue();
  }

}
