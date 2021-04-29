package com.davidlozzi.calculator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class History {
  private String key;
  private String value;

  @JsonCreator
  public History(@JsonProperty("key") String key, @JsonProperty("value") String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return this.key;
  }

  public String getValue() {
    return this.value;
  }
}
