package com.davidlozzi.search.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

  @JsonIgnore
  public boolean isValid() {
    return notEmpty(this.character) && notEmpty(this.dialog);
  }

  @JsonIgnore
  public String getValueByName(String fieldName) {
    switch (fieldName) {
      case "movie":
        return this.movie;
      case "character":
        return this.character;
      default:
        return null;
    }
  }
}
