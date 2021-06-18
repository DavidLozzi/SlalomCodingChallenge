package com.davidlozzi.vendit.Interfaces;

import com.davidlozzi.vendit.Implementations.Candy;
import com.davidlozzi.vendit.Implementations.Soda;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = Candy.class, name = "Candy"), @Type(value = Soda.class, name = "Soda") })
public interface Product {
  String getName();

  Double getPrice();

  void setName(String name);

  void setPrice(Double price);
}
