package com.davidlozzi.search.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataEntry {
  private String character;
  private String dialog;
  private String movie;
  private Integer position;

  private Boolean notEmpty(String value) {
    return value != null && !value.trim().isEmpty();
  }

  public boolean isValid() {
    return notEmpty(this.character) && notEmpty(this.dialog);
  }
}
