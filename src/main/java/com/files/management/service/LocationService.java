package com.files.management.service;

import com.files.management.entity.Location;
import com.files.management.exception.DuplicateLocationException;
import com.files.management.mapper.LocationMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class LocationService {
    private final LocationMapper locationMapper;

    public LocationService(LocationMapper locationMapper){
        this.locationMapper = locationMapper;
    }

    public Location insert(String locationName, int shelfNumber){
        // 複合ユニークバリデーション
        if (locationMapper.isMaterialUnique(locationName, shelfNumber)) {
            throw new DuplicateLocationException("Location with location:" + locationName
                    + " and shelfNumber:" + shelfNumber + " already exists");
        }
        Location location = new Location(locationName, shelfNumber);
        locationMapper.insert(location);
        return location;
    }
}
