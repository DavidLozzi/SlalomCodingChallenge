package com.davidlozzi.search;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Download {
  public static void files() { // TODO use custom error handler
    try {
      String[] files = { "https://appdev-code-challenge.s3.amazonaws.com/challenge3_search/SW_EpisodeIV.txt",
          "https://appdev-code-challenge.s3.amazonaws.com/challenge3_search/SW_EpisodeV.txt",
          "https://appdev-code-challenge.s3.amazonaws.com/challenge3_search/SW_EpisodeVI.txt" };

      for (String url : files) { // should cache files instead of downloading on every request
        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
        String fileName = getFilename(url);
        FileOutputStream fileOutputStream = new FileOutputStream("3_search/data/" + fileName);
        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
          fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
        fileOutputStream.close();
        System.out.println("downloaded " + url);
      }
    } catch (Exception ex) {
      throw new RuntimeException("Download file error: " + ex.toString());
    }
  }

  private static String getFilename(String path) {
    Pattern pat = Pattern.compile("/(SW_Episode[VI]*.txt)");
    Matcher mat = pat.matcher(path);

    String fileName = "";
    if (mat.find()) {
      fileName = mat.group(1);
    }
    return fileName;
  }
}
