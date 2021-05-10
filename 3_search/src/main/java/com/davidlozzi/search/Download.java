package com.davidlozzi.search;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.davidlozzi.search.models.ApiException;

import org.springframework.http.HttpStatus;

public class Download {
  public static void files() throws ApiException {
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
    } catch (ApiException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new ApiException("Download file error: " + ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private static String getFilename(String path) throws ApiException {
    try {
      Pattern pat = Pattern.compile("/(SW_Episode[VI]*.txt)");
      Matcher mat = pat.matcher(path);

      String fileName = "";
      if (mat.find()) {
        fileName = mat.group(1);
      }
      return fileName;
    } catch (Exception ex) {
      throw new ApiException("filename parse error: " + ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
