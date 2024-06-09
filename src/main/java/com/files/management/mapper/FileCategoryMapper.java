package com.files.management.mapper;

import com.files.management.entity.FileCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileCategoryMapper {

  @Insert("INSERT INTO file_categories (privacy_type, storage_year) VALUE (#{privacyType}, #{storageYear})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(FileCategory fileCategory);

  @Select("SELECT COUNT(*) FROM file_categories WHERE privacy_type = #{privacyType} AND storage_year = #{storageYear}")
  boolean isNotFileCategoryUnique(@Param("privacyType") String privacyType,
      @Param("storageYear") int storageYear);
}
