package com.davidlozzi.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.davidlozzi.search.models.DataEntry;
import com.davidlozzi.search.models.Relevancy;
import com.davidlozzi.search.models.ResultItem;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SearchFiles {
  // ideally this would be indexed in a performant data store
  // but since it's just 3 files and a quick coding challenge, I'll do it at request
  private static List<DataEntry> createSearchableData() throws FileNotFoundException {
    File dataFolder = new File("3_search/data");
    List<DataEntry> data = new ArrayList<DataEntry>();

    for (File file : dataFolder.listFiles()) {
      if (file.canRead()) {
        Scanner reader = new Scanner(file);
        reader.nextLine(); // skip the first line
        while (reader.hasNextLine()) {
          String nextLine = reader.nextLine();
          Pattern pat = Pattern.compile("\"([a-z0-9\\s\\.,\\?!'-:#;]*)\"", Pattern.CASE_INSENSITIVE); // anything in quotes that is not a quote
          Matcher mat = pat.matcher(nextLine);

          DataEntry newData = new DataEntry();
          Integer looper = 0;
          while (mat.find()) {
            String val = mat.group(1);
            switch (looper) {
              case 0:
                newData.setPosition(Integer.parseInt(val));
                break;
              case 1:
                newData.setCharacter(val);
                break;
              case 2:
                newData.setDialog(val);
                break;
              default:
                System.out.print("parsing file error on " + looper + " " + nextLine);
                break;
            }
            looper++;
          }
          if (!newData.isValid()) { // if the dialog contains " then the above regex doens't find the data
            System.out.print("bad data in " + file.getName() + ": " + nextLine + System.lineSeparator());
          } else {
            newData.setMovie(file.getName());
            data.add(newData);
          }
        }
        reader.close();
      }
    }

    return data;
  }

  private static List<ResultItem> applyRelevancy(List<ResultItem> results)
      throws JsonParseException, JsonMappingException, IOException {
    FileReader reader = new FileReader("3_search/Relevancy.json");
    ObjectMapper objectMapper = new ObjectMapper();
    List<Relevancy> relevancyWeights = objectMapper.readValue(reader, new TypeReference<List<Relevancy>>() {
    });

    for (ResultItem result : results) {
      Integer score = 0;
      for (Relevancy rel : relevancyWeights) {
        if (rel.getCharacter() != null && rel.getCharacter().equals(result.getData().getCharacter())) {
          score += rel.getScore();
        }
        if (rel.getMovie() != null && rel.getMovie().equals(result.getData().getMovie())) {
          score += rel.getScore();
        }
      }
      result.setRank(score);
    }
    return results;
  }

  public static List<ResultItem> search(String keyword) throws JsonParseException, JsonMappingException, IOException {
    List<ResultItem> results = new ArrayList<ResultItem>();
    List<DataEntry> dataList = createSearchableData();

    for (DataEntry data : dataList) {
      try {
        if (data.getDialog().toLowerCase().indexOf(keyword.toLowerCase()) > -1) {
          Pattern pat = Pattern.compile(
              "(([a-z0-9\\.,\\?!'-:#;]*\\s){0,2}[,]*" + keyword + "['a-z.,]*(\\s[a-z0-9\\.,\\?!'-:#;]*){0,2})",
              Pattern.CASE_INSENSITIVE);
          Matcher mat = pat.matcher(data.getDialog());
          if (mat.find()) {
            ResultItem result = new ResultItem();
            result.setText(mat.group(1).replaceAll(keyword, "<strong>" + keyword + "</strong>"));
            result.setData(data);
            results.add(result);
          }
        }
      } catch (Exception ex) {
        System.out.print("search " + data.getPosition() + data.getDialog());
        throw ex;
      }
    }
    results = applyRelevancy(results);
    Collections.sort(results);
    return results;
  }
}
