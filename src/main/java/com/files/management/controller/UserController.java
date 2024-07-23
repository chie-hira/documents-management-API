package com.files.management.controller;

import com.files.management.controller.request.UserRequest;
import com.files.management.controller.response.UserResponse;
import com.files.management.entity.User;
import com.files.management.service.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/users")
  public ResponseEntity<UserResponse> insert(
      @RequestBody @Valid UserRequest userRequest,
      UriComponentsBuilder uriComponentsBuilder
  ) {
    User user = userService.insert(
        userRequest.getName(),
        userRequest.getEmail(),
        userRequest.getPassword(),
        userRequest.getIsAdmin()
    );
    URI uriUser = uriComponentsBuilder.path("Users/{id}").buildAndExpand(user.getId()).toUri();
    int newId = user.getId();
    UserResponse body = new UserResponse(
        "アカウントを登録しました",
        newId,
        userRequest.getName(),
        userRequest.getEmail(),
        user.getIsAdmin()
    );
    return ResponseEntity.created(uriUser).body(body);
  }
}
