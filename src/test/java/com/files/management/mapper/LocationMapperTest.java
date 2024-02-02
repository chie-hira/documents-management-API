package com.files.management.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.files.management.entity.Location;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import java.util.List;
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
    Location location = new Location("事務室", "棚-3");
    locationMapper.insert(location);
    assertThat(location.getId()).isGreaterThan(5);
  }

  @Test
  @DataSet(value = "datasets/insert_locations.yml, datasets/insert_files.yml")
  @Transactional
  void 保存場所名が重複する場合は例外がthrowされ登録出来ないこと() {
    int initialRowCount = locationMapper.getCount();

    Location location = new Location("事務室", "棚-1");
    assertThatThrownBy(() -> locationMapper.insert(location))
        .isInstanceOf(DuplicateKeyException.class);

    int finalRowCount = locationMapper.getCount();

    assertThat(initialRowCount).isEqualTo(finalRowCount);
  }

  @Test
  @DataSet(value = "datasets/insert_locations.yml, datasets/insert_files.yml")
  @Transactional
  void 保存場所を更新できること() {
    Location location = new Location(1, "事務室", "棚-4");
    locationMapper.update(location);

//    Optional<Location> afterUpdate = locationMapper.findById(location.getId());
    Optional<Location> afterUpdate = locationMapper.findById(1);

    assertThat(afterUpdate.get().getId()).isEqualTo(1);
    assertThat(afterUpdate.get().getLocation()).isEqualTo("事務室");
    assertThat(afterUpdate.get().getShelfNumber()).isEqualTo("棚-4");
  }

  @Test
  @DataSet(value = "datasets/insert_locations.yml, datasets/insert_files.yml")
  @Transactional
  void findByIdを確認() {
    Optional<Location> check = locationMapper.findById(2);

    assertThat(check.get().getId()).isEqualTo(2);
    assertThat(check.get().getLocation()).isEqualTo("倉庫");
    assertThat(check.get().getShelfNumber()).isEqualTo("スチール書庫-2");
  }

  @Test
  @DataSet(value = "datasets/insert_locations.yml, datasets/insert_files.yml")
  @Transactional
  void findAllを確認() {
    List<Location> locations = locationMapper.findAll();

    Location secondLocation = locations.get(1);

    assertThat(secondLocation.getId()).isEqualTo(2);
    assertThat(secondLocation.getLocation()).isEqualTo("倉庫");
    assertThat(secondLocation.getShelfNumber()).isEqualTo("スチール書庫-2");
  }
}
