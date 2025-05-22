package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.config.PlayerConfig;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.factory.PlayerFactory;
import edu.ntnu.idi.idatt.persistence.reader.PlayerFileReader;
import edu.ntnu.idi.idatt.persistence.writer.PlayerFileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        List<PlayerConfig> configs = service.createPlayerConfigs(names, tokens);

        assertEquals(2, configs.size());
        assertEquals("P1", configs.get(0).getPlayer().getName());
    }

    /**
     * Verifies that valid player names and tokens pass the validation check.
     */
    @Test
    void testIsPlayerConfigDataValidSuccessfully() {
        boolean isValid = service.isPlayerConfigDataValid(
                List.of("P1", "P2"), List.of("Red", "Blue")
        );
        assertTrue(isValid);
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
        List<String> names = List.of("P1");
        List<String> tokens = List.of("Unknown");
        assertThrows(RuntimeException.class, () -> service.createPlayerConfigs(names, tokens));
    }

    /**
     * Verifies that mismatched list sizes for names and tokens fail validation.
     */
    @Test
    void testIsPlayerConfigDataValidMismatchedSizes() {
        boolean result = service.isPlayerConfigDataValid(
                List.of("P1"), List.of("Blue", "Red")
        );
        assertFalse(result);
    }

    /**
     * Verifies that duplicate names or tokens fail validation.
     */
    @Test
    void testIsPlayerConfigDataValidDuplicateNamesOrTokens() {
        boolean result = service.isPlayerConfigDataValid(
                List.of("P1", "p1"), List.of("Blue", "Blue")
        );
        assertFalse(result);
    }

    //------------ Edge case ------------

    /**
     * Verifies that empty player names fail validation.
     */
    @Test
    void testIsPlayerConfigDataValidWithEmptyNames() {
        boolean result = service.isPlayerConfigDataValid(List.of("", "P2"), List.of("Blue", "Red"));
        assertFalse(result);
    }

    /**
     * Verifies that null tokens fail validation.
     */
    @Test
    void testIsPlayerConfigDataValidWithNullToken() {
        List<String> tokens = new ArrayList<>();
        tokens.add(null);
        boolean result = service.isPlayerConfigDataValid(List.of("P1"),tokens);
        assertFalse(result);
    }

    //------------ Dummy ------------


    /**
     * Dummy implementation of {@link PlayerFileReader} that simulates reading from CSV files.
     */
    private static class DummyReader extends PlayerFileReader {
        @Override
        public List<String[]> readFromCSV(File file) {
            List<String[]> data = new ArrayList<>();

            if (file.getName().equals("too_few.csv")) {
                data.add(new String[] { "OnlyOne" });
            } else {
                data.add(new String[] { "P1", "Red" });
                data.add(new String[] { "P2", "Blue" });
            }

            return data;
        }
    }

    /**
     * Dummy implementation of {@link PlayerFileWriter} that does nothing.
     */
    private static class DummyWriter extends PlayerFileWriter {
        @Override
        public void writeToCSV(List<String[]> data, File file, String[] headers) {
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
