package com.davidlozzi.vendit.Models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApiErrorResponse {
  private final String message;
  private final Integer httpStatusCode;
}
