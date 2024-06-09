package com.files.management.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.files.management.entity.Location;
import com.files.management.exception.DuplicateException;
import com.files.management.exception.LocationNotFoundException;
import com.files.management.mapper.LocationMapper;
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
    // テスト対象を実行
    Location insertLocation = locationService.insert("物置", "棚-1");

    // 検証
    assertThat(insertLocation).isNotNull();
    assertThat(insertLocation.getLocation()).isEqualTo("物置");
    assertThat(insertLocation.getShelfNumber()).isEqualTo("棚-1");

    verify(locationMapper, times(1)).insert(insertLocation);
  }

  @Test
  public void 既に登録済みの保存場所を登録しようとしたとき例外が投げられること() {
    // モックと検証のデータを用意
    String existingLocationName = "事務室";
    String existingShelfNumber = "棚-1";
    String exceptionMessage = "Location with location:事務室 and shelfNumber:棚-1 already exists";

    // モックの動作を定義
    doReturn(true).when(locationMapper)
        .isNotLocationUnique(existingLocationName, existingShelfNumber);

    // 検証　テスト対象を実行するとexceptionが発生するのでいきなり検証
    assertThatThrownBy(() -> locationService.insert(existingLocationName, existingShelfNumber))
        .isInstanceOf(DuplicateException.class)
        .hasMessage(exceptionMessage);
    verify(locationMapper, times(1)).isNotLocationUnique(existingLocationName, existingShelfNumber);
    verify(locationMapper, never()).insert(any(Location.class));
  }

  @Test
  public void 更新できること() {
    // モックと検証のデータを用意
    int id = 1;
    String locationName = "新しい場所";
    String shelfNumber = "新棚-1";
    Location existingLocation = new Location(id, "倉庫", "スチール書庫-1");
    Optional<Location> optionalLocation = Optional.of(existingLocation);

    // マッパーの動きを定義
    when(locationMapper.findById(id)).thenReturn(optionalLocation);
    when(locationMapper.isNotLocationUnique(locationName, shelfNumber)).thenReturn(false);
    doNothing().when(locationMapper).update(any(Location.class));

    // テスト対象を実行
    Location updatedLocation = locationService.update(id, locationName, shelfNumber);

    // 検証
    assertThat(updatedLocation).isNotNull();
    assertThat(updatedLocation.getId()).isEqualTo(id);
    assertThat(updatedLocation.getLocation()).isEqualTo(locationName);
    assertThat(updatedLocation.getShelfNumber()).isEqualTo(shelfNumber);

    // メソッドが正しく呼び出されたか検証
    verify(locationMapper, times(1)).findById(id);
    verify(locationMapper, times(1)).isNotLocationUnique(locationName, shelfNumber);
    verify(locationMapper, times(1)).update(any(Location.class));
  }

  @Test
  public void 既に登録済みの保存場所に更新しようとしたとき例外が投げられること() {
    // モックと検証のデータを用意
    int id = 1;
    String locationName = "新しい場所";
    String shelfNumber = "新棚-1";
    String existingLocationName = "既存の場所";
    String existingShelfNumber = "既存棚-1";
    Location existingLocation = new Location(id, existingLocationName, existingShelfNumber);
    Optional<Location> optionalLocation = Optional.of(existingLocation);
    String exceptionMessage =
        "Location with location:" + locationName + " and shelfNumber:" + shelfNumber
            + " already exists";

    // マッパーの動きを定義
    when(locationMapper.findById(id)).thenReturn(optionalLocation);
    when(locationMapper.isNotLocationUnique(locationName, shelfNumber)).thenReturn(true);

    // 検証
    assertThatThrownBy(() -> locationService.update(id, locationName, shelfNumber))
        .isInstanceOf(DuplicateException.class)
        .hasMessage(exceptionMessage);
    verify(locationMapper, times(1)).isNotLocationUnique(locationName, shelfNumber);
    verify(locationMapper, never()).update(any(Location.class));
  }

  @Test
  public void 削除できること() {
    // モックと検証のデータを用意
    int id = 1;
    String locationName = "新しい場所";
    String shelfNumber = "新棚-1";
    Location existingLocation = new Location(id, "倉庫", "スチール書庫-1");
    Optional<Location> optionalLocation = Optional.of(existingLocation);

    // マッパーの動きを定義
    when(locationMapper.findById(id)).thenReturn(optionalLocation);
    doNothing().when(locationMapper).delete(anyInt());

    // テスト対象を実行
    locationService.delete(optionalLocation.get().getId());

    // スタブの呼び出しを検証
    verify(locationMapper, times(1)).delete(1);
  }

  @Test
  public void 存在しないIDを指定したとき例外が発生すること() {
    // モックと検証のデータを用意
    int id = 99;
    Optional<Location> optionalLocation = Optional.empty();
    String exceptionMessage = "location not found";

    // マッパーの動きを定義
    when(locationMapper.findById(id)).thenReturn(optionalLocation);

    // 検証
    assertThatThrownBy(() -> locationService.delete(id))
        .isInstanceOf(LocationNotFoundException.class)
        .hasMessage(exceptionMessage);
    verify(locationMapper, never()).delete(anyInt());
  }
}
