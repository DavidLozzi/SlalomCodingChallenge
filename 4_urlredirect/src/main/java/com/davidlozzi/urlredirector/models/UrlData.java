package com.davidlozzi.urlredirector.models;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class UrlData extends RepresentationModel<UrlData> {
  private final String shortUrl;
  private final String originalUrl;
  private long usageCount = 0;
}
