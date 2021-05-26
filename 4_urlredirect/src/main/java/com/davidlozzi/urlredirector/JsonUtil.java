package com.davidlozzi.urlredirector;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.davidlozzi.urlredirector.models.AnalyticData;
import com.davidlozzi.urlredirector.models.UrlData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {
  private static String urlsFile = "4_urlredirect/urls.json";
  private static String analyticFile = "4_urlredirect/analytics.json";

  public static void saveUrl(UrlData url) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    List<UrlData> urlList = new ArrayList<UrlData>();
    try {
      FileReader reader = new FileReader(urlsFile);
      urlList = new ObjectMapper().readValue(reader, ArrayList.class);
    } catch (Exception ex) {
      System.out.print("saveUrl " + ex);
    }

    urlList.add(url);

    objectMapper.writeValue(new File(urlsFile), urlList);
  }

  public static UrlData getUrlByShort(String shorturl) throws Exception {
    try {
      FileReader reader = new FileReader(urlsFile);
      ObjectMapper objectMapper = new ObjectMapper();
      List<UrlData> urlList = objectMapper.readValue(reader, new TypeReference<List<UrlData>>() {
      });

      for (UrlData url : urlList) {
        if (url.getShortUrl().equals(shorturl)) {
          AnalyticData aData = new AnalyticData(shorturl, new Date());
          saveAnalytics(aData);
          long usageCount = getAnalyticCount(shorturl);
          url.setUsageCount(usageCount);
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
      FileReader reader = new FileReader(urlsFile);
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      List<UrlData> urlList = objectMapper.readValue(reader, new TypeReference<List<UrlData>>() {
      });

      for (UrlData url : urlList) {
        if (url.getOriginalUrl().equals(longUrl)) {
          return url;
        }
      }
    } catch (Exception ex) {
      System.out.print("getUrlByLong " + ex);
      return null;
    }
    return null;
  }

  public static void saveAnalytics(AnalyticData data) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    List<AnalyticData> analyticList = new ArrayList<AnalyticData>();
    try {
      FileReader reader = new FileReader(analyticFile);
      analyticList = new ObjectMapper().readValue(reader, ArrayList.class);
    } catch (Exception ex) {
      System.out.print("saveAnalytics " + ex);
    }

    analyticList.add(data);

    objectMapper.writeValue(new File(analyticFile), analyticList);
  }

  public static long getAnalyticCount(String shorturl) throws Exception {
    try {
      FileReader reader = new FileReader(analyticFile);
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      List<AnalyticData> analyticList = objectMapper.readValue(reader, new TypeReference<List<AnalyticData>>() {
      });

      return analyticList.stream().filter(a -> a.getShortUrl().equals(shorturl)).count();
    } catch (Exception ex) {
      System.out.print("getAnalyticCount " + ex);
      return 0;
    }
  }

  public static List<AnalyticData> getAnalyticByShort(String shorturl) throws Exception {
    try {
      FileReader reader = new FileReader(analyticFile);
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      List<AnalyticData> analyticList = objectMapper.readValue(reader, new TypeReference<List<AnalyticData>>() {
      });

      return analyticList.stream().filter(a -> a.getShortUrl().equals(shorturl)).collect(Collectors.toList());
    } catch (Exception ex) {
      System.out.print("getAnalyticByShort " + ex);
      return null;
    }
  }
}
