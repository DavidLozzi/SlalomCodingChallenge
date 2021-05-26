package com.davidlozzi.urlredirector.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApiErrorResponse {
  private final String message;
  private final Integer httpStatusCode;
}
