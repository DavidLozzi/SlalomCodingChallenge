package com.davidlozzi.search.models;

import org.springframework.http.HttpStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ApiException extends Exception {
  private HttpStatus httpStatus;

  public ApiException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

}
