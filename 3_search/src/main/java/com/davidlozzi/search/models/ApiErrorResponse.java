package com.davidlozzi.search.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiErrorResponse {
  private String message;
  private Integer httpStatusCode;

  public ApiErrorResponse(String message, Integer httpStatusCode) {
    this.message = message;
    this.httpStatusCode = httpStatusCode;
  }
}
