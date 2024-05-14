package com.files.management.service;

import com.files.management.entity.Location;
import com.files.management.exception.DuplicateLocationException;
import com.files.management.exception.LocationNotFoundException;
import com.files.management.mapper.LocationMapper;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class LocationService {

  private final LocationMapper locationMapper;

  public LocationService(LocationMapper locationMapper) {
    this.locationMapper = locationMapper;
  }

  public Location insert(String locationName, String shelfNumber) {
    // 複合ユニークバリデーション
    if (locationMapper.isNotLocationUnique(locationName, shelfNumber)) {
      throw new DuplicateLocationException("Location with location:" + locationName
          + " and shelfNumber:" + shelfNumber + " already exists");
    }
    Location location = new Location(locationName, shelfNumber);
    locationMapper.insert(location);
    return location;
  }

  public Location update(int id, String locationName, String shelfNumber) {
    Optional<Location> location = this.locationMapper.findById(id);
    location.orElseThrow(
        () -> new LocationNotFoundException("location not found", HttpStatus.NOT_FOUND));

    if (locationMapper.isNotLocationUnique(locationName, shelfNumber)) {
      throw new DuplicateLocationException("Location with location:" + locationName
          + " and shelfNumber:" + shelfNumber + " already exists");
    }
    Location updateLocation = new Location(id, locationName, shelfNumber);
    locationMapper.update(updateLocation);
    return updateLocation;
  }

  public void delete(int id) {
    locationMapper.findById(id)
        .orElseThrow(
            () -> new LocationNotFoundException("location not found", HttpStatus.NOT_FOUND));

    Optional<Location> location = this.locationMapper.findById(id);
    location.orElseThrow(
        () -> new LocationNotFoundException("location not found", HttpStatus.NOT_FOUND));
    // Fileまわりを実装したらfilesにlocationがあるときは削除できないようにする
    locationMapper.delete(id);
  }

  public Location show(int id) {
    return locationMapper.findById(id)
        .orElseThrow(
            () -> new LocationNotFoundException("location not found", HttpStatus.NOT_FOUND));
  }
}
