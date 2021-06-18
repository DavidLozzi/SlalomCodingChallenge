package com.davidlozzi.vendit.Implementations;

import com.davidlozzi.vendit.Interfaces.Product;

public class Candy implements Product {
  private String name;
  private Double price;
  private String type;

  public void Product(String name, Double price) {
    this.name = name;
    this.price = price;
    this.type = "Candy";
  }

  public void Product() {

  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Double getPrice() {
    return this.price;
  }

  public String getType() {
    return this.type;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setPrice(Double price) {
    this.price = price;
  }
}
