package edu.ntnu.idi.idatt.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ManualService {
  public String loadManualText(String path) {
    InputStream input = getClass().getResourceAsStream(path);
    if (input == null) {
      return "Could not load manual: Resource not found.";
    }
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
      return reader.lines().reduce("", (acc, line) -> acc + line + "\n");
    } catch (IOException e) {
      return "Could not load manual: An I/O error occurred.";
    }
  }
}
