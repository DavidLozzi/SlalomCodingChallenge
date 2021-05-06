package com.davidlozzi.pomodoro;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {
  private static String timerFile = "2_pomodoro/timers.json";

  public static void addTimer(String key, Timer timer) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    List<Timer> timerList = new ArrayList<Timer>();
    try {
      FileReader reader = new FileReader(timerFile);
      timerList = new ObjectMapper().readValue(reader, ArrayList.class);
    } catch (Exception ex) {
      System.out.print("addTimer " + ex);
    }

    timerList.add(timer);

    objectMapper.writeValue(new File(timerFile), timerList);
  }

  public static Timer getTimer(long timerId) throws Exception {
    try {
      FileReader reader = new FileReader(timerFile);
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      List<Timer> timerList = objectMapper.readValue(reader, new TypeReference<List<Timer>>() {
      });

      for (Timer timer : timerList) {
        if (timer.getId() == timerId) {
          return timer;
        }
      }
    } catch (Exception ex) {
      System.out.print("getTimer " + ex);
      throw ex;
    }
    return null;
  }
}
