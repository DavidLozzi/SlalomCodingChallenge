package com.davidlozzi.calculator;

import java.io.Serializable;

public class History implements Serializable {
  private String key;
  private String value;

  public History() {

  }

  public History(String key, String value) {
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
