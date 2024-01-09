package com.files.management.controller;

import com.files.management.controller.request.LocationRequest;
import com.files.management.controller.response.LocationResponse;
import com.files.management.entity.Location;
import com.files.management.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService){
        this.locationService = locationService;
    }

    @PostMapping("/locations")
    public ResponseEntity<LocationResponse> insert(@RequestBody @Valid LocationRequest locationRequest, UriComponentsBuilder uriComponentsBuilder){
        Location location = locationService.insert(locationRequest.getLocation(), locationRequest.getShelfNumber());
        URI uriLocation = uriComponentsBuilder.path("locations/{id}").buildAndExpand(location.getId()).toUri();
        LocationResponse body = new LocationResponse("保存場所情報を登録しました");
        return ResponseEntity.created(uriLocation).body(body);
    }
}
