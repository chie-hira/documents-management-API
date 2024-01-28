package com.files.management.exception;

import org.springframework.http.HttpStatus;

public class LocationNotFoundException extends RuntimeException {

  private HttpStatus status;

  public LocationNotFoundException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
