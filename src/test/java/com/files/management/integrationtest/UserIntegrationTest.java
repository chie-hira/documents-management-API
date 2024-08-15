package com.files.management.integrationtest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.files.management.mapper.UserMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DBRider
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserMapper userMapper;

  @Test
  @DataSet(value = "datasets/insert_users.yml, datasets/insert_files.yml")
  @Transactional
  void ユーザーを登録できること() throws Exception {
    String userName = "テストユーザー";
    String email = "test@email.com";
    String password = "password";
    boolean isAdmin = false;

    // モック設定
    when(userMapper.isNotUserUnique(userName, email)).thenReturn(false);

    // リクエスト作成
    String requestBody = """
        {
          "name": "%s",
          "email": "%s",
          "password": "%s",
          "isAdmin": %s
        }
        """.formatted(userName, email, password, isAdmin);

    // 実行
    String response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(StandardCharsets.UTF_8);

    // 検証
    JSONAssert.assertEquals("""
        {
          "message": "アカウントを登録しました",
          "id": 3,
          "name": "テストユーザー",
          "email": "test@email.com",
          "isAdmin": false
         }
        """, response, new CustomComparator(JSONCompareMode.STRICT,
        new Customization("id", ((o1, o2) -> true))
    ));
  }
}
