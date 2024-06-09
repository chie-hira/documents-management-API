package com.files.management.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.files.management.entity.FileCategory;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FileCategoryMapperTest {

  @Autowired
  FileCategoryMapper fileCategoryMapper;

  @Test
  @DataSet(value = "datasets/insert_file_categories.yml, datasets/insert_files.yml")
  @Transactional
  void 新しいファイルカテゴリを登録できること() {
    FileCategory fileCategory = new FileCategory("公開", 3);
    fileCategoryMapper.insert(fileCategory);
    assertThat(fileCategory.getId()).isGreaterThan(5);
  }
}
