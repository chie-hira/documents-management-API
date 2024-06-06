package com.files.management.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.files.management.entity.FileCategory;
import com.files.management.exception.DuplicateException;
import com.files.management.mapper.FileCategoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FileCategoryServiceTest {

  @InjectMocks
  FileCategoryService fileCategoryService;

  @Mock
  FileCategoryMapper fileCategoryMapper;

  @Test
  public void 登録できること() {
    // テスト対象を実行
    FileCategory insertFileCategory = fileCategoryService.insert("公開", 5);

    // 検証
    assertNotNull(insertFileCategory);
    assertEquals("公開", insertFileCategory.getPrivacyType());
    assertEquals(5, insertFileCategory.getStorageYear());

    verify(fileCategoryMapper, times(1)).insert(insertFileCategory);
  }

  @Test
  public void 既に登録済みのファイルカテゴリを登録しようとしたとき例外が投げられること() {
    // モックと検証のデータを用意
    String existingPrivacyType = "非公開";
    int existingStorageYear = 3;
    String exceptionMessage = "すでに登録済みのファイル分類情報です";

    // モックの動作を定義
    when(fileCategoryMapper.isNotFileCategoryUnique(existingPrivacyType,
        existingStorageYear)).thenReturn(true);

    // 検証　テスト対象を実行するとexceptionが発生するのでいきなり検証
    assertThatThrownBy(() -> fileCategoryService.insert(existingPrivacyType, existingStorageYear))
        .isInstanceOf(DuplicateException.class)
        .hasMessage(exceptionMessage);
  }

}
