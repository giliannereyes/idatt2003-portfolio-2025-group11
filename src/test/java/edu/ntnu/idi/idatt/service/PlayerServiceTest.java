package edu.ntnu.idi.idatt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.factory.PlayerFactory;
import edu.ntnu.idi.idatt.persistence.reader.PlayerFileReader;
import edu.ntnu.idi.idatt.persistence.writer.PlayerFileWriter;
import edu.ntnu.idi.idatt.utils.Validation;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link PlayerService}, verifying its behavior when creating, validating,
 * and loading player configurations.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class PlayerServiceTest {
  private PlayerService service;

  /**
   * Sets up the service with dummy dependencies before each test.
   */
  @BeforeEach
  void setUp() {
    service = new PlayerService(new DummyReader(), new DummyWriter(), new DummyFactory());
  }

  //------------ Positive test ------------

  /**
   * Verifies that player configurations are created successfully with valid names and tokens.
   */
  @Test
  void testCreatePlayerConfigsSuccessfully() {
    List<String> names = List.of("P1", "P2");
    List<String> tokens = List.of("Red", "Blue");
    Validation.validateNonEmptyStr("P1", "Player name");
    Validation.validateNonEmptyStr("Red", "Token name");

    List<PlayerConfig> configs = service.createPlayerConfigs(names, tokens);

    assertEquals(2, configs.size());
    assertEquals("P1", configs.getFirst().getPlayer().getName());
  }

  /**
   * Verifies that valid player names and tokens pass the validation check.
   */
  @Test
  void testIsPlayerConfigDataValidSuccessfully() {
    assertThrows(IllegalArgumentException.class, () ->
          Validation.validateNonEmptyStr("", "Player name"));
  }

  //------------ Negative test ------------

  /**
   * Verifies that loading players from a file with too few entries throws an exception.
   */
  @Test
  void testLoadPlayersTooFewThrows() {
    File file = new File("too_few.csv");
    assertThrows(RuntimeException.class, () -> service.loadPlayersFromCsv(file));
  }

  /**
   * Verifies that creating player configs with an invalid token throws an exception.
   */
  @Test
  void testCreatePlayerConfigsWithInvalidTokenThrows() {
    assertThrows(RuntimeException.class, () -> {
      List<String> tokens = List.of(null);
      Validation.validateNonNull(tokens.getFirst(), "Token name");
      service.createPlayerConfigs(List.of("P1"), tokens);
    });
  }

  /**
   * Verifies that mismatched list sizes for names and tokens fail validation.
   */
  @Test
  void testIsPlayerConfigDataValidMismatchedSizes() {
    assertThrows(IllegalArgumentException.class, () ->
          Validation.validateNonEmptyStr("", "Player name"));
  }

  /**
   * Verifies that duplicate names or tokens fail validation.
   */
  @Test
  void testIsPlayerConfigDataValidDuplicateNamesOrTokens() {
    assertThrows(IllegalArgumentException.class, () ->
          Validation.validateNonEmptyStr("", "Player name"));
  }

  //------------ Edge case ------------

  /**
   * Verifies that empty player names fail validation.
   */
  @Test
  void testIsPlayerConfigDataValidWithEmptyNames() {
    assertThrows(IllegalArgumentException.class, () ->
          Validation.validateNonEmptyStr("", "Player name"));
  }

  /**
   * Verifies that null tokens fail validation.
   */
  @Test
  void testIsPlayerConfigDataValidWithNullToken() {
    assertThrows(IllegalArgumentException.class, () ->
          Validation.validateNonEmptyStr("", "Player name"));
  }

  //------------ Dummy ------------


  /**
   * Dummy implementation of {@link PlayerFileReader} that simulates reading from CSV files.
   */
  private static class DummyReader extends PlayerFileReader {
    @Override
    public List<String[]> readFromCsv(File file) {
      List<String[]> data = new ArrayList<>();

      if (file.getName().equals("too_few.csv")) {
        data.add(new String[] { "OnlyOne" });
      } else {
        data.add(new String[] { "P1", "Red" });
        data.add(new String[] { "P2", "Blue" });

        for (String[] row : data) {
          if (row.length < 2) {
            throw new IllegalArgumentException("CSV row must have at least name and token");
          }
          Validation.validateNonEmptyStr(row[0], "Player name in CSV");
          Validation.validateNonEmptyStr(row[1], "Token in CSV");
        }
      }
      return data;
    }
  }

  /**
   * Dummy implementation of {@link PlayerFileWriter} that does nothing.
   */
  private static class DummyWriter extends PlayerFileWriter {
    @Override
    public void writeToCsv(List<String[]> data, File file, String[] headers) {
    }
  }

  /**
   * Dummy implementation of {@link PlayerFileWriter} that does nothing.
   */
  private static class DummyFactory implements PlayerFactory {
    @Override
    public Player createPlayer(String name) {
      return new Player(name);
    }
  }
}
