package com.davidlozzi.search.models;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Filter {
  private String filterName;
  private List<FilterValue> values;
}
