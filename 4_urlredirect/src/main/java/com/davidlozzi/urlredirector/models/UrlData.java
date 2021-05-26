package com.davidlozzi.urlredirector.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class UrlData {
  private final String shortUrl;
  private final String originalUrl;
}
