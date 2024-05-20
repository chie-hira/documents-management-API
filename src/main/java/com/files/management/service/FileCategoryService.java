package com.files.management.service;

import com.files.management.entity.FileCategory;
import com.files.management.exception.DuplicateException;
import com.files.management.mapper.FileCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class FileCategoryService {

  private final FileCategoryMapper fileCategoryMapper;

  public FileCategoryService(FileCategoryMapper fileCategoryMapper) {
    this.fileCategoryMapper = fileCategoryMapper;
  }

  public FileCategory insert(String privacyType, int storageYear) {
    // 複合ユニークバリデーション
    if (fileCategoryMapper.isNotFileCategoryUnique(privacyType, storageYear)) {
      throw new DuplicateException("FileCategory with privacyType:" + privacyType
          + " and storageYear:" + storageYear + " already exists");
    }
    FileCategory fileCategory = new FileCategory(privacyType, storageYear);
    fileCategoryMapper.insert(fileCategory);
    return fileCategory;
  }

}
