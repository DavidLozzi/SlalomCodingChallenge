package com.davidlozzi.urlredirector;

import java.util.Random;

public class Utils {
  public static String getNewId() {
    String newId = "";
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    Random rand = new Random();
    for (var i = 0; i < 10; i++) {
      int rando = rand.nextInt(alphabet.length());
      newId += alphabet.charAt(rando);
    }

    return newId;
  }
}
