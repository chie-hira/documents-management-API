package com.files.management.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.files.management.entity.Location;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LocationMapperTest {

  @Autowired
  LocationMapper locationMapper;

  @Test
  @DataSet(value = "datasets/insert_locations.yml, datasets/insert_files.yml")
  @Transactional
  void 新しい保存場所を登録できること() {
    Location location = new Location("物置", "スチール書庫-1");
    locationMapper.insert(location);
    assertThat(location.getId()).isGreaterThan(5);
  }

  @Test
  @DataSet(value = "datasets/insert_locations.yml, datasets/insert_files.yml")
  @Transactional
  void 保存場所名が重複する場合は例外がthrowされ登録できないこと() {
    int initialRowCount = locationMapper.getCount();
    Location location = new Location("倉庫", "スチール書庫-1");
    assertThatThrownBy(() -> locationMapper.insert(location))
        .isInstanceOf(DuplicateKeyException.class);

    int finalRowCount = locationMapper.getCount();
    assertThat(initialRowCount).isEqualTo(finalRowCount);
  }

  @Test
  @DataSet(value = "datasets/update_locations.yml, datasets/insert_files.yml")
  @Transactional
  void 保存場所を更新できること() {
    Location location = new Location(1, "物置", "棚-1");
    locationMapper.update(location);

    Optional<Location> afterUpdate = locationMapper.findById(1);
//    LocalDateTime updateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    LocalDateTime updateTime = LocalDateTime.now(ZoneId.of("Asia/Tokyo"))
        .truncatedTo(ChronoUnit.SECONDS);

    assertThat(afterUpdate.get().getId()).isEqualTo(1);
    assertThat(afterUpdate.get().getLocation()).isEqualTo("物置");
    assertThat(afterUpdate.get().getShelfNumber()).isEqualTo("棚-1");
    assertThat(afterUpdate.get().getUpdatedAt()).isEqualTo(updateTime);
  }

  @Test
  @DataSet(value = "datasets/update_locations.yml, datasets/insert_files.yml")
  @Transactional
  void 更新後の保存場所名が重複する場合は例外がthrowされ更新できないこと() {
    int initialRowCount = locationMapper.getCount();
    Location location = new Location(1, "倉庫", "スチール書庫-2");
    assertThatThrownBy(() -> locationMapper.update(location))
        .isInstanceOf(DuplicateKeyException.class);

    int finalRowCount = locationMapper.getCount();
    assertThat(initialRowCount).isEqualTo(finalRowCount);
  }

  @Test
  @DataSet(value = "datasets/update_locations.yml, datasets/insert_files.yml")
  @Transactional
  void 指定したIDから保存場所を取得できること() {
    Location location = new Location(1, "物置", "棚-1");
    int existingId = 1;
    String existingLocation = "倉庫";
    String existingShelfNumber = "スチール書庫-1";

    Optional<Location> findLocation = locationMapper.findById(existingId);

    assertThat(findLocation.get().getId()).isEqualTo(existingId);
    assertThat(findLocation.get().getLocation()).isEqualTo(existingLocation);
    assertThat(findLocation.get().getShelfNumber()).isEqualTo(existingShelfNumber);
  }

  @Test
  @DataSet(value = "datasets/update_locations.yml, datasets/insert_files.yml")
  @Transactional
  void 指定したIDが見つからないとき空のOptionalが返されること() {
    assertThat(locationMapper.findById(99)).isEmpty();
  }
}
