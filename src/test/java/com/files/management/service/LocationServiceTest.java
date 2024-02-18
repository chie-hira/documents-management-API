package com.files.management.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.files.management.entity.Location;
import com.files.management.exception.DuplicateLocationException;
import com.files.management.mapper.LocationMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

  @InjectMocks
  LocationService locationService;

  @Mock
  LocationMapper locationMapper;

  @Test
  public void 登録できること() {
    Location insertLocation = locationService.insert("事務室", "棚-1");

    assertThat(insertLocation).isNotNull();
    assertThat(insertLocation.getLocation()).isEqualTo("事務室");
    assertThat(insertLocation.getShelfNumber()).isEqualTo("棚-1");

    verify(locationMapper, times(1)).insert(insertLocation);
  }

  @Test
  public void 既に登録済みの保存場所を登録しようとしたとき例外が投げられること() {
    String existingLocationName = "事務室";
    String existingShelfNumber = "棚-1";
    doReturn(true).when(locationMapper).isMaterialUnique(existingLocationName, existingShelfNumber);

    assertThatThrownBy(() -> locationService.insert(existingLocationName, existingShelfNumber))
        .isInstanceOf(DuplicateLocationException.class)
        .hasMessage("Location with location:" + existingLocationName
            + " and shelfNumber:" + existingShelfNumber + " already exists");

    verify(locationMapper, times(1)).isMaterialUnique(existingLocationName, existingShelfNumber);

    verify(locationMapper, never()).insert(any(Location.class));
  }

  @Test
  public void 更新できること() {
    // マッパーの動きをモックで定義
    int id = 1;
    String locationName = "新しい場所";
    String shelfNumber = "新棚-1";
    LocalDateTime currentTime = LocalDateTime.now();
    Location existingLocation = new Location(id, "倉庫", "スチール書庫-1");
    Optional<Location> optionalLocation = Optional.of(existingLocation);

    // findById(),一意であること,update()のマッパーの動きを定義
    when(locationMapper.findById(id)).thenReturn(optionalLocation);
    when(locationMapper.isMaterialUnique(locationName, shelfNumber)).thenReturn(false);
    doNothing().when(locationMapper).update(any(Location.class));

    // テスト対象を実行
    Location updatedLocation = locationService.update(id, locationName, shelfNumber);

    // 検証
    assertThat(updatedLocation).isNotNull();
    assertThat(updatedLocation.getId()).isEqualTo(id);
    assertThat(updatedLocation.getLocation()).isEqualTo(locationName);
    assertThat(updatedLocation.getShelfNumber()).isEqualTo(shelfNumber);
    assertThat(updatedLocation.getUpdatedAt()).isAfterOrEqualTo(currentTime);

    // メソッドが正しく呼び出されたか検証
    verify(locationMapper, times(1)).findById(id);
    verify(locationMapper, times(1)).isMaterialUnique(locationName, shelfNumber);
    verify(locationMapper, times(1)).update(any(Location.class));
  }
}
