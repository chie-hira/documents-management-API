package com.files.management.mapper;

import com.files.management.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Insert("INSERT INTO users (name, email, password, is_admin) VALUE (#{name}, #{email}, #{password}, #{isAdmin})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(User user);

  @Select("SELECT COUNT(*) FROM users WHERE name = #{name} AND email = #{email}")
  boolean isNotUserUnique(@Param("name") String name, @Param("email") String email);

  @Select("SELECT COUNT(*) FROM users WHERE email = #{email}")
  boolean existsByEmail(@Param("email") String email);

}
