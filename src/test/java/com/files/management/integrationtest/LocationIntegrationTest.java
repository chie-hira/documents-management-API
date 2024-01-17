package com.files.management.integrationtest;

import com.files.management.mapper.LocationMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
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

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DBRider
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LocationIntegrationTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private LocationMapper locationMapper;

	@Test
	@DataSet(value = "datasets/insert_locations.yml, datasets/insert_files.yml")
	@Transactional
	void 保存場所を登録できること() throws Exception {
		String locationName = "TestLocation";
		String shelfNumber = "TestShelfNumber";

		when(locationMapper.isMaterialUnique(locationName, shelfNumber)).thenReturn(false);

		String response = mockMvc.perform(MockMvcRequestBuilders.post("/locations")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"location\":\"" + locationName + "\",\"shelfNumber\":\"" + shelfNumber + "\"}"))
				.andExpect(status().isCreated())
				.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

		JSONAssert.assertEquals("""
				  {
				    "message": "保存場所情報を登録しました",
				    "id": 6,
				    "location": "TestLocation",
				    "shelfNumber": "TestShelfNumber"
				  }
				  """, response, new CustomComparator(JSONCompareMode.STRICT,
				new Customization("id", ((o1, o2) -> true))
		));

	}

	@Test
	@DataSet(value = "datasets/insert_locations.yml, datasets/insert_files.yml")
	@Transactional
	void 保存場所が空で保存場所を作成しようとしたとき例外が投げられること() throws Exception {
		String locationName = "";
		String shelfNumber = "TestShelfNumber";

		String response = mockMvc.perform(MockMvcRequestBuilders.post("/locations")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"location\":\"" + locationName + "\",\"shelfNumber\":\"" + shelfNumber + "\"}"))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

		JSONAssert.assertEquals("""
				{
				  "status": "400",
				  "message": "location: location is required",
				  "timestamp": "2024-01-17T22:47:08.854416+09:00[Asia/Tokyo]",
				  "error": "Bad Request",
				  "path": "/locations"
				}
				""", response, new CustomComparator(JSONCompareMode.STRICT,
				new Customization("timestamp", ((o1, o2) -> true))));
	}

	@Test
	@DataSet(value = "datasets/insert_locations.yml, datasets/insert_files.yml")
	@Transactional
	void 棚番号が空で保存場所を作成しようとしたとき例外が投げられること() throws Exception {
		// locationが空の場合のテスト
		String locationName = "TestLocation";
		String shelfNumber = "";

		String response = mockMvc.perform(MockMvcRequestBuilders.post("/locations")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"location\":\"" + locationName + "\",\"shelfNumber\":\"" + shelfNumber + "\"}"))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

		JSONAssert.assertEquals("""
				{
				  "status": "400",
				  "message": "shelfNumber: location is required",
				  "timestamp": "2024-01-17T22:47:08.854416+09:00[Asia/Tokyo]",
				  "error": "Bad Request",
				  "path": "/locations"
				}
				""", response, new CustomComparator(JSONCompareMode.STRICT,
				new Customization("timestamp", ((o1, o2) -> true))));
	}

	@Test
	@DataSet(value = "datasets/insert_locations.yml, datasets/insert_files.yml")
	@Transactional
	void 重複する保存場所を登録しようとしたとき例外が投げられること() throws Exception {
		String locationName = "TestLocation";
		String shelfNumber = "TestShelfNumber";

		when(locationMapper.isMaterialUnique(locationName, shelfNumber)).thenReturn(true);

		String response = mockMvc.perform(MockMvcRequestBuilders.post("/locations")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"location\":\"" + locationName + "\",\"shelfNumber\":\"" + shelfNumber + "\"}"))
				.andExpect(status().isUnprocessableEntity())
				.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

		JSONAssert.assertEquals("""
				{
				  "timestamp": "2024-01-17T22:39:14.555576+09:00[Asia/Tokyo]",
				  "message": "Location with location:TestLocation and shelfNumber:TestShelfNumber already exists",
				  "status": "422",
				  "path": "/locations",
				  "error": "Unprocessable Entity"
				}
				""", response, new CustomComparator(JSONCompareMode.STRICT,
				new Customization("timestamp", ((o1, o2) -> true))));
	}
}
