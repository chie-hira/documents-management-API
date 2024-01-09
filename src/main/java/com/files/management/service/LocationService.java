package com.files.management.service;

import com.files.management.entity.Location;
import com.files.management.mapper.LocationMapper;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    private final LocationMapper locationMapper;

    public LocationService(LocationMapper locationMapper){
        this.locationMapper = locationMapper;
    }

    public Location insert(String locationName, int shelfNumber){
        Location location = new Location(locationName, shelfNumber);
        locationMapper.insert(location);
        return location;
    }
}
