package com.davidlozzi.urlredirector;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import com.davidlozzi.urlredirector.models.AnalyticData;
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

      UrlData url = JsonUtil.getUrlByShort(longurl);
      if (url == null) {
        url = JsonUtil.getUrlByLong(longurl);
      }

      while (url == null) {
        String shorturl = Utils.getNewId();
        System.out.print("trying " + shorturl);
        if (JsonUtil.getUrlByShort(shorturl) == null) {
          url = new UrlData(shorturl, longurl);
          JsonUtil.saveUrl(url);
        }
      }

      url.add(linkTo(methodOn(UrlredirectorApplication.class).shrink(longurl)).withSelfRel());
      url.add(linkTo(methodOn(UrlredirectorApplication.class).expand(url.getShortUrl())).withRel("expand"));
      url.add(linkTo(methodOn(UrlredirectorApplication.class).analytics(url.getShortUrl())).withRel("analytics"));
      return new ResponseEntity<>(url, HttpStatus.OK);
    } catch (Exception ex) {
      ApiErrorResponse err = new ApiErrorResponse(ex.toString(), 500);
      return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/redirect/expand")
  @ResponseBody
  public ResponseEntity expand(@RequestParam(value = "shorturl", defaultValue = "") String shorturl) {
    try {
      if (shorturl.trim().length() == 0) {
        ApiErrorResponse err = new ApiErrorResponse("No shorturl provided", 400);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
      }
      UrlData url = JsonUtil.getUrlByShort(shorturl);
      if (url != null) {

        url.add(linkTo(methodOn(UrlredirectorApplication.class).expand(shorturl)).withSelfRel());
        url.add(linkTo(methodOn(UrlredirectorApplication.class).analytics(shorturl)).withRel("analytics"));
        return new ResponseEntity<>(url, HttpStatus.OK);
      }
      ApiErrorResponse err = new ApiErrorResponse("shorturl " + shorturl + " was not found", 404);
      return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);

    } catch (Exception ex) {
      ApiErrorResponse err = new ApiErrorResponse(ex.toString(), 500);
      return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/redirect/analytics")
  @ResponseBody
  public ResponseEntity analytics(@RequestParam(value = "shorturl", defaultValue = "") String shorturl) {
    try {
      if (shorturl.trim().length() == 0) {
        ApiErrorResponse err = new ApiErrorResponse("No shorturl provided", 400);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
      }
      List<AnalyticData> analytics = JsonUtil.getAnalyticByShort(shorturl);
      if (analytics != null) {
        return new ResponseEntity<>(analytics, HttpStatus.OK);
      }
      ApiErrorResponse err = new ApiErrorResponse("shorturl " + shorturl + " was not found", 404);
      return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);

    } catch (Exception ex) {
      ApiErrorResponse err = new ApiErrorResponse(ex.toString(), 500);
      return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
