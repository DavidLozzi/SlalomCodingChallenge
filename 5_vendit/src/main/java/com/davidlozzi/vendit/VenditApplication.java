package com.davidlozzi.vendit;

import com.davidlozzi.vendit.Models.ApiErrorResponse;
import com.davidlozzi.vendit.Models.Inventory;
import com.davidlozzi.vendit.utils.JsonUtil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VenditApplication {

  public static void main(String[] args) {
    SpringApplication.run(VenditApplication.class, args);
  }

  @GetMapping("/inventory")
  @ResponseBody
  public ResponseEntity getInventory() {
    try {
      Inventory inventory = JsonUtil.getInventory();
      return new ResponseEntity<>(inventory, HttpStatus.OK);
    } catch (Exception ex) {
      ApiErrorResponse err = new ApiErrorResponse(ex.toString(), 500);
      System.out.println(ex);
      return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/vend")
  @ResponseBody
  public ResponseEntity vend(@RequestParam(value = "itemPosition", defaultValue = "") String itemPosition) {

  }
}
