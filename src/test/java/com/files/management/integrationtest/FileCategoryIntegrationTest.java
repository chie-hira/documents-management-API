package com.files.management.integrationtest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.files.management.mapper.FileCategoryMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@DBRider
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FileCategoryIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  private FileCategoryMapper fileCategoryMapper;

  @Test
  @DataSet(value = "datasets/insert_file_categories.yml, datasets/insert_files.yml")
  @Transactional
  void ファイル分類情報を登録できること() throws Exception {
    String privacyType = "公開";
    int storageYear = 3;

    when(fileCategoryMapper.isNotFileCategoryUnique(privacyType, storageYear)).thenReturn(false);

    String requestBody = """
        {
          "privacyType": "公開",
          "storageYear": 3
        }
        """;

    String response = mockMvc.perform(MockMvcRequestBuilders.post("/file_categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
            {
              "message": "ファイル分類情報を登録しました",
              "id": 6,
              "privacyType": "公開",
              "storageYear": 3
            }
        """, response, new CustomComparator(JSONCompareMode.STRICT,
        new Customization("id", ((o1, o2) -> true))
    ));
  }

  @Test
  @DataSet(value = "datasets/insert_file_categories.yml, datasets/insert_files.yml")
  @Transactional
  void 開示区分を空でファイル分類情報を作成しようとしたとき例外が投げられること()
      throws Exception {
    String privacyType = "";
    int storageYear = 5;

    // リクエスト作成
    String requestBody = """
        {
          "privacyType": "",
          "storageYear": 5
        }
        """;

    when(fileCategoryMapper.isNotFileCategoryUnique(privacyType, storageYear)).thenReturn(false);

    String response = mockMvc.perform(MockMvcRequestBuilders.post("/file_categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
            {
              "path": "/file_categories",
              "status": "400",
              "message": "privacyType: 開示区分は必須です",
              "timestamp": "2024-05-26T21:26:07.273723+09:00[Asia/Tokyo]",
              "error": "Bad Request"
            }
        """, response, new CustomComparator(JSONCompareMode.STRICT,
        new Customization("timestamp", ((o1, o2) -> true))
    ));
  }

  @Test
  @DataSet(value = "datasets/insert_file_categories.yml, datasets/insert_files.yml")
  @Transactional
  void 保存年が空でファイル分類情報を作成しようとしたとき例外が投げられること()
      throws Exception {
    String privacyType = "非公開";
    Integer storageYear = null;

    // リクエスト作成
    String requestBody = """
        {
          "privacyType": "非公開",
          "storageYear": null
        }
        """;

    String response = mockMvc.perform(MockMvcRequestBuilders.post("/file_categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
            {
              "path": "/file_categories",
              "status": "400",
              "message": "storageYear: 保存年数は必須です",
              "timestamp": "2024-05-26T21:26:07.273723+09:00[Asia/Tokyo]",
              "error": "Bad Request"
            }
        """, response, new CustomComparator(JSONCompareMode.STRICT,
        new Customization("timestamp", ((o1, o2) -> true))
    ));
  }

  @Test
  @DataSet(value = "datasets/insert_file_categories.yml, datasets/insert_files.yml")
  @Transactional
  void 重複するファイル分類情報を作成しようとしたとき例外が投げられること() throws Exception {
    String privacyType = "非公開";
    int storageYear = 3;

    // リクエスト作成
    String requestBody = """
        {
          "privacyType": "非公開",
          "storageYear": 3
        }
        """;

    when(fileCategoryMapper.isNotFileCategoryUnique(privacyType, storageYear)).thenReturn(true);

    String response = mockMvc.perform(MockMvcRequestBuilders.post("/file_categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isConflict())
        .andReturn()
        .getResponse()
        .getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
            {
              "path": "/file_categories",
              "status": "409",
              "message": "すでに登録済みのファイル分類情報です",
              "timestamp": "2024-05-26T21:26:07.273723+09:00[Asia/Tokyo]",
              "error": "Conflict"
            }
        """, response, new CustomComparator(JSONCompareMode.STRICT,
        new Customization("timestamp", ((o1, o2) -> true))));
  }

  @Test
  @DataSet(value = "datasets/insert_file_categories.yml, datasets/insert_files.yml")
  @Transactional
  void 開示区分を公開非公開以外でファイル分類情報を作成しようとしたとき例外が投げられること()
      throws Exception {
    String privacyType = "限定公開";
    int storageYear = 3;

    // リクエスト作成
    String requestBody = """
        {
          "privacyType": "限定公開",
          "storageYear": 3
        }
        """;

    when(fileCategoryMapper.isNotFileCategoryUnique(privacyType, storageYear)).thenReturn(false);

    String response = mockMvc.perform(MockMvcRequestBuilders.post("/file_categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
            {
              "path": "/file_categories",
              "status": "400",
              "message": "privacyType: 開示区分は公開か非公開で指定してください",
              "timestamp": "2024-05-26T21:26:07.273723+09:00[Asia/Tokyo]",
              "error": "Bad Request"
            }
        """, response, new CustomComparator(JSONCompareMode.STRICT,
        new Customization("timestamp", ((o1, o2) -> true))
    ));
  }
}
