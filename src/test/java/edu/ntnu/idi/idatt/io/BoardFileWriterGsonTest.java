package edu.ntnu.idi.idatt.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.ntnu.idi.idatt.model.actions.LadderAction;
import edu.ntnu.idi.idatt.model.entities.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the BoardFileWriterGson class.
 *
 * @version 0.1
 * @since 0.2
 * @author Gilianne Reyes
 * @see BoardFileWriterGson
 */
public class BoardFileWriterGsonTest {
    Board validBoard;
    Path tempFile;

    /**
     * Set up a valid board before each test.
     */
    @BeforeEach
    public void setup() {
        // Arrange a valid board with tiles and a tile action.
        validBoard = new Board();
        validBoard.setName("Test board");
        validBoard.setDescription("Test description");
        validBoard.initializeBoard(10);
        // Adds a ladder action on tile 2 with destination tile 5.
        validBoard.addTileAction(2, new LadderAction(validBoard.getTile(5)));
    }

    /**
     * Clean up the temporary file after each test.
     *
     * @throws IOException if an I/O error occurs.
     */
    @AfterEach
    public void tearDown() throws IOException {
        if (tempFile != null) {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Test writing a valid board to a file.
     *
     * <p>Expected: The file should contain the board's name, description, tiles
     * and the tiles' ID, next tile ID, and action (if present).</p>
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void testWriterWithValidBoard() throws IOException {
        BoardFileWriter writer = new BoardFileWriterGson();
        tempFile = Files.createTempFile("board", ".json");
        writer.writeBoard(tempFile, validBoard);
        String content = Files.readString(tempFile);
        JsonObject boardJson = JsonParser.parseString(content).getAsJsonObject();

        checkBoardMetaData(boardJson);
        checkTilesArray(boardJson.getAsJsonArray("tiles"));
    }

    /**
     * Checks the board's name and description are present in the JSON object.
     *
     * @param boardJson is the JSON object representing the board.
     */
    private void checkBoardMetaData(JsonObject boardJson) {
        assertEquals("Test board", boardJson.get("name").getAsString(), "Board name should be 'Test board'.");
        assertEquals("Test description", boardJson.get("description").getAsString(), "Board description should be 'Test description'.");
        assertTrue(boardJson.has("tiles"), "Board JSON should contain a 'tiles' key.");
        assertTrue(boardJson.get("tiles").isJsonArray(), "'tiles' should be a JsonArray.");
    }

    /**
     * Checks the tiles array in the JSON object. Also checks if tile 2 has a ladder action
     * as expected.
     *
     * <p>Expected: The tiles array should contain 10 tiles. Tile 2 should have a ladder action
     * with the correct type and destination tile ID.</p>
     *
     * @param tilesJson is the JSON array representing the tiles.
     */
    private void checkTilesArray(JsonArray tilesJson) {
        assertEquals(10, tilesJson.size(), "There should be 10 tiles in the JSON array.");

        // Find tile with tile id 2. (Assumes that the tiles are not in order)
        JsonObject tile2Json = null;
        for (JsonElement element : tilesJson) {
            JsonObject tileJson = element.getAsJsonObject();
            if (tileJson.get("id").getAsInt() == 2) {
                tile2Json = tileJson;
                break;
            }
        }
        assertNotNull(tile2Json, "Tile 2 should be present in the tiles array.");
        assertTrue(tile2Json.has("action"), "Tile 2 should have an action.");
        JsonObject actionJson = tile2Json.getAsJsonObject("action");
        assertEquals(LadderAction.actionType, actionJson.get("type").getAsString(), "Tile 2's action should be a ladder.");
        assertEquals(5, actionJson.get("destinationTileId").getAsInt(), "Tile 2's action should have a destination of 5.");
    }

    /**
     * Test writing a board with no tiles.
     *
     * <p>Expected: The file should contain the board's name, description,
     * and an empty map of tiles.</p>
     */
    @Test
    public void testWriterWithEmptyBoard() throws IOException {
        Board emptyBoard = new Board();
        BoardFileWriter writer = new BoardFileWriterGson();
        tempFile = Files.createTempFile("emptyBoard", ".json");

        writer.writeBoard(tempFile, emptyBoard);
        String content = Files.readString(tempFile);
        JsonObject boardJson = JsonParser.parseString(content).getAsJsonObject();

        assertTrue(boardJson.has("tiles"), "Board JSON should contain a 'tiles' key.");
        JsonArray tilesArray = boardJson.getAsJsonArray("tiles");
        assertEquals(0, tilesArray.size(), "Tiles array should be empty for an empty board.");
    }

    // ----- Negative tests -----

    /**
     * Test writing a null board to a file.
     *
     * <p>Expected: An IllegalArgumentException should be thrown.</p>
     */
    @Test
    public void testWriterWithNullBoard() {
        BoardFileWriter writer = new BoardFileWriterGson();
        tempFile = assertDoesNotThrow(() -> Files.createTempFile("board", ".json"));
        assertThrows(IllegalArgumentException.class, () -> writer.writeBoard(tempFile, null));
    }

    /**
     * Test writing a board to a null file.
     *
     * <p>Expected: An IllegalArgumentException should be thrown.</p>
     */
    @Test
    public void testWriterWithNullFile() {
        BoardFileWriter writer = new BoardFileWriterGson();
        assertThrows(IllegalArgumentException.class, () -> writer.writeBoard(null, validBoard));
    }
}
