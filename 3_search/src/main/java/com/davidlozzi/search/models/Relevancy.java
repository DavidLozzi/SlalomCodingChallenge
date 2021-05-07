package com.davidlozzi.search.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Relevancy {
  private String character;
  private String movie;
  private Integer score;
}
