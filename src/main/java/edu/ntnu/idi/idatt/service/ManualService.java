package edu.ntnu.idi.idatt.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Service class responsible for loading manual text resources from the classpath.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class ManualService {

  /**
   * Loads the contents of a manual text file from the given resource path.
   *
   * @param path the path to the manual file within the classpath (e.g., "/manuals/game_manual.txt")
   * @return the contents of the manual as a String, or an error message if loading fails
   */
  public String loadManualText(String path) {
    if (path == null || path.isBlank()) {
      return "Could not load manual: Resource not found.";
    }
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
