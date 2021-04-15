package com.davidlozzi.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicLong;

import javax.script.ScriptException;

@SpringBootApplication
@RestController
public class CalculatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(CalculatorApplication.class, args);
  }

  private final AtomicLong counter = new AtomicLong();

  @GetMapping("/calculate")
  public Answer calculate(@RequestParam(value = "calculation", defaultValue = "") String calculation)
      throws ScriptException { // why throwing these exceptions?
    if (calculation != null && calculation != "") {
      double result = Math.calculate(calculation);
      return new Answer(counter.incrementAndGet(), result, calculation); // how do i effectively throw a meaningful error back to user?
    } else {
      return new Answer(0, 0, "Provide a calculation parameter. Use %2B for +, otherwise, everything else works."); // would like to use a different class for this
    }
  }
}
