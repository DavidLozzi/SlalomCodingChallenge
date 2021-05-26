package com.davidlozzi.urlredirector;

import com.davidlozzi.urlredirector.models.ApiErrorResponse;
import com.davidlozzi.urlredirector.models.UrlData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UrlredirectorApplication {

  public static void main(String[] args) {
    SpringApplication.run(UrlredirectorApplication.class, args);
  }

  @GetMapping("/redirect/shrink")
  @ResponseBody
  public ResponseEntity shrink(@RequestParam(value = "longurl", defaultValue = "") String longurl) {
    try {
      if (longurl.trim().length() == 0) {
        ApiErrorResponse err = new ApiErrorResponse("No longurl provided", 400);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
      }
      // no slash so it may be a short URL
      if (JsonUtil.getUrlByShort(longurl) != null) {
        UrlData url = JsonUtil.getUrlByShort(longurl);
        return new ResponseEntity<>(url, HttpStatus.FOUND);
      }
      Boolean saved = false;
      UrlData url = null;
      while (!saved) {
        String shortUrl = Utils.getNewId();
        System.out.print("trying " + shortUrl);
        if (JsonUtil.getUrlByShort(shortUrl) == null) {
          url = new UrlData(shortUrl, longurl);
          JsonUtil.saveUrl(url);
          saved = true;
        }
      }

      return new ResponseEntity<>(url, HttpStatus.OK);
    } catch (Exception ex) {
      ApiErrorResponse err = new ApiErrorResponse(ex.toString(), 500);
      return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/redirect/expand")
  @ResponseBody
  public ResponseEntity expand(@RequestParam(value = "shortUrl", defaultValue = "") String shortUrl) {
    try {
      if (shortUrl.trim().length() == 0) {
        ApiErrorResponse err = new ApiErrorResponse("No shortUrl provided", 400);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
      }
      // no slash so it may be a short URL
      if (JsonUtil.getUrlByShort(shortUrl) != null) {
        UrlData url = JsonUtil.getUrlByShort(shortUrl);
        return new ResponseEntity<>(url, HttpStatus.OK);
      }
      ApiErrorResponse err = new ApiErrorResponse("shortUrl " + shortUrl + " was not found", 404);
      return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);

    } catch (Exception ex) {
      ApiErrorResponse err = new ApiErrorResponse(ex.toString(), 500);
      return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
