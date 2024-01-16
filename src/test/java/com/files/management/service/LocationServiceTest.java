package com.files.management.service;

import com.files.management.entity.Location;
import com.files.management.exception.DuplicateLocationException;
import com.files.management.mapper.LocationMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {
    @InjectMocks
    LocationService locationService;

    @Mock
    LocationMapper locationMapper;

    @Test
    public void 登録できること(){
        Location insertLocation = locationService.insert("事務室", "棚-1");

        assertThat(insertLocation).isNotNull();
        assertThat(insertLocation.getLocation()).isEqualTo("事務室");
        assertThat(insertLocation.getShelfNumber()).isEqualTo("棚-1");

        verify(locationMapper, times(1)).insert(insertLocation);
    }

    @Test
    public void 既に登録済みの保存場所を登録しようとしたとき例外が投げられること(){
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

}
