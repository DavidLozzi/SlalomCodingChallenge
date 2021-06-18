package com.davidlozzi.vendit.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.davidlozzi.vendit.Models.Inventory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
  private static String inventoryFile = "5_vendit/inventory.json";

  public static void save(Inventory inventory) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(new File(inventoryFile), inventory);
  }

  public static Inventory getInventory() throws JsonParseException, JsonMappingException, IOException {
    Inventory inventory;
    FileReader reader = new FileReader(inventoryFile);
    ObjectMapper objectMapper = new ObjectMapper();
    inventory = objectMapper.readValue(reader, new TypeReference<Inventory>() {
    });

    return inventory;
  }
}
