package com.davidlozzi.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

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
  public ResponseEntity<Answer> calculate(@RequestParam(value = "calculation", defaultValue = "") String calculation)
      throws ScriptException {
    try {
      if (calculation != null && calculation != "") {
        double result = Math.calculate(calculation);
        Answer answer = new Answer(counter.incrementAndGet(), result, calculation);
        return new ResponseEntity<>(answer, HttpStatus.OK);
      } else {
        Answer answer = new Answer(0, 0,
            "make sure to provide your equation in the parameter ?calculation=2*2. use %2B for +");
        return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
      }
    } catch (ScriptException ex) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Math error: " + ex.toString());
    } catch (Exception ex) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + ex.toString());
    }
  }
}
