package com.davidlozzi.calculator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
  private static String historyFile = "1_calculator/history.json";

  public static void setString(String key, String value) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    List<History> historyList = new ArrayList<History>();
    if (!key.equals("1")) {
      try {
        FileReader reader = new FileReader(historyFile);
        historyList = new ObjectMapper().readValue(reader, ArrayList.class);
      } catch (Exception ex) {

      }
    }
    History history = new History(key, value);
    historyList.add(history);

    objectMapper.writeValue(new File(historyFile), historyList);
  }

  public static String getString(String key) {
    String historyValue = "0";
    try {
      FileReader reader = new FileReader(historyFile);
      List<History> historyList = new ObjectMapper().readValue(reader, new TypeReference<List<History>>() {
      });

      for (History history : historyList) {
        if (history.getKey().equals(key)) {
          return history.getValue();
        }
      }
    } catch (Exception ex) {
      System.out.print(ex);
    }
    return historyValue;

  }

  public static String getAll() {
    String historyValue = "";
    try {
      FileReader reader = new FileReader(historyFile);
      List<History> historyList = new ObjectMapper().readValue(reader, new TypeReference<List<History>>() {
      });

      for (History history : historyList) {
        historyValue += "#" + history.getKey() + ": " + history.getValue() + "; ";
      }
    } catch (Exception ex) {
      System.out.print(ex);
      historyValue = "n/a";
    }
    return historyValue;

  }
}
