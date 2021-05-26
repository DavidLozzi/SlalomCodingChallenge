package com.davidlozzi.urlredirector;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.davidlozzi.urlredirector.models.UrlData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {
  private static String file = "4_urlredirect/urls.json";

  public static void saveUrl(UrlData url) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    List<UrlData> urlList = new ArrayList<UrlData>();
    try {
      FileReader reader = new FileReader(file);
      urlList = new ObjectMapper().readValue(reader, ArrayList.class);
    } catch (Exception ex) {
      System.out.print("addTimer " + ex);
    }

    urlList.add(url);

    objectMapper.writeValue(new File(file), urlList);
  }

  public static UrlData getUrlByShort(String shortUrl) throws Exception {
    try {
      FileReader reader = new FileReader(file);
      ObjectMapper objectMapper = new ObjectMapper();
      List<UrlData> urlList = objectMapper.readValue(reader, new TypeReference<List<UrlData>>() {
      });

      for (UrlData url : urlList) {
        if (url.getShortUrl().equals(shortUrl)) {
          return url;
        }
      }
    } catch (Exception ex) {
      System.out.print("getUrlByShort " + ex);
      return null;
    }
    return null;
  }

  public static UrlData getUrlByLong(String longUrl) throws Exception {
    try {
      FileReader reader = new FileReader(file);
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      List<UrlData> urlList = objectMapper.readValue(reader, new TypeReference<List<UrlData>>() {
      });

      for (UrlData url : urlList) {
        if (url.getOriginalUrl() == longUrl) {
          return url;
        }
      }
    } catch (Exception ex) {
      System.out.print("getUrlByShort " + ex);
      throw ex;
    }
    return null;
  }
}
