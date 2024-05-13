package com.files.management.controller;

import com.files.management.controller.request.LocationRequest;
import com.files.management.controller.response.LocationResponse;
import com.files.management.entity.Location;
import com.files.management.service.LocationService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class LocationController {

  private final LocationService locationService;

  public LocationController(LocationService locationService) {
    this.locationService = locationService;
  }

  @PostMapping("/locations")
  public ResponseEntity<LocationResponse> insert(
      @RequestBody @Valid LocationRequest locationRequest,
      UriComponentsBuilder uriComponentsBuilder) {
    Location location = locationService.insert(locationRequest.getLocation(),
        locationRequest.getShelfNumber());
    URI uriLocation = uriComponentsBuilder.path("locations/{id}").buildAndExpand(location.getId())
        .toUri();
    int newId = location.getId();
    LocationResponse body = new LocationResponse("保存場所情報を登録しました", newId,
        locationRequest.getLocation(), locationRequest.getShelfNumber());
    return ResponseEntity.created(uriLocation).body(body);
  }

  @PutMapping("/locations/{locationId}")
  public ResponseEntity<LocationResponse> update(
      @PathVariable("locationId") int locationId,
      @RequestBody @Valid LocationRequest locationRequest,
      UriComponentsBuilder uriComponentsBuilder) {
    Location location = locationService.update(
        locationId,
        locationRequest.getLocation(),
        locationRequest.getShelfNumber()
    );
    URI uriLocation = uriComponentsBuilder.path("locations/{id}")
        .buildAndExpand(location.getId())
        .toUri();
    LocationResponse body = new LocationResponse(
        "保存場所情報を更新しました",
        locationId,
        locationRequest.getLocation(),
        locationRequest.getShelfNumber());
    return ResponseEntity.ok().location(uriLocation).body(body);
  }

  @DeleteMapping("/locations/{id}")
  public ResponseEntity<Void> delete(
      @PathVariable("id") int id
  ) {
    Location location = locationService.show(id);
    if (location == null) {
      return ResponseEntity.notFound().build();
    }

    locationService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
