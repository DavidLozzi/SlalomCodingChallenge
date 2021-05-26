package com.davidlozzi.urlredirector.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticData {
  private String shortUrl;
  private Date date;
}
