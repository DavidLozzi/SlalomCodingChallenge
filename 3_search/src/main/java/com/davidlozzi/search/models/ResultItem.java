package com.davidlozzi.search.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultItem implements Comparable<ResultItem> {
  private String text;
  private Integer rank;
  private DataEntry data;

  public int compareTo(ResultItem a) {
    return a.rank - this.rank;
  }
}
