package com.davidlozzi.search.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterValue implements Comparable<FilterValue> {
  private String value;
  private Integer count;

  public FilterValue(String value, Integer count) {
    this.value = value;
    this.count = count;
  }

  public int compareTo(FilterValue a) {
    return a.count - this.count;
  }
}
