package com.files.management.controller;

import com.files.management.controller.response.FileCategoryResponse;
import com.files.management.entity.FileCategory;
import com.files.management.service.FileCategoryService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class FileCategoryController {

  private final FileCategoryService fileCategoryService;

  public FileCategoryController(FileCategoryService fileCategoryService) {
    this.fileCategoryService = fileCategoryService;
  }

  @PostMapping("/fileCategories")
  public ResponseEntity<FileCategoryResponse> insert(
      @RequestBody FileCategory fileCategoryRequest,
      UriComponentsBuilder uriComponentsBuilder) {
    FileCategory fileCategory = fileCategoryService.insert(fileCategoryRequest.getPrivacyType(),
        fileCategoryRequest.getStorageYear());
    URI uriFileCategory = uriComponentsBuilder.path("fileCategories/{id}")
        .buildAndExpand(fileCategoryRequest.getId())
        .toUri();
    int newId = fileCategory.getId();

    FileCategoryResponse body = new FileCategoryResponse("ファイル分類情報を登録しました", newId,
        fileCategoryRequest.getPrivacyType(), fileCategoryRequest.getStorageYear());

    return ResponseEntity.created(uriFileCategory).body(body);
  }
}
