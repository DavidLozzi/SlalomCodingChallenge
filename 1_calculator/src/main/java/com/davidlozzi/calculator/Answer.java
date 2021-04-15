package com.davidlozzi.calculator;

public class Answer {
  private long id;
  private double answer;
  private String calculation;

  public Answer(long id, double answer, String calculation) {
    this.id = id;
    this.answer = answer;
    this.calculation = calculation;
  }

  public long getId() {
    return id;
  }

  public double getAnswer() {
    return answer;
  }

  public String getCalculation() {
    return calculation;
  }
}
