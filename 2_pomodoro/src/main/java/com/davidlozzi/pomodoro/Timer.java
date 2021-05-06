package com.davidlozzi.pomodoro;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.hateoas.RepresentationModel;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Timer extends RepresentationModel<Timer> {
  private long id;
  private String startTime;
  private long duration;
  private double remainingSeconds;

  public Timer(long id, String startTime, long duration) {
    this.id = id;
    this.startTime = startTime;
    this.duration = duration;
  }

  public double getRemainingSeconds() throws ParseException {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime endTime = LocalDateTime.parse(this.startTime).plus(this.duration, ChronoUnit.MINUTES);
    double diff = ChronoUnit.SECONDS.between(now, endTime);
    System.out.print(diff);
    System.out.print(" ");
    return diff;
  }

  public long getId() {
    return this.id;
  }

  public String getStartTime() {
    return this.startTime.toString();
  }

  public long getDuration() {
    return this.duration;
  }
}
