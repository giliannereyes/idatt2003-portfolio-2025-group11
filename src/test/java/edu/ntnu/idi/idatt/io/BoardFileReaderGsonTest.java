package edu.ntnu.idi.idatt.io;

import edu.ntnu.idi.idatt.exceptions.BoardParsingException;
import edu.ntnu.idi.idatt.model.actions.LadderAction;
import edu.ntnu.idi.idatt.model.actions.ResetAction;
import edu.ntnu.idi.idatt.model.entities.Board;
import edu.ntnu.idi.idatt.model.factory.LadderActionFactory;
import edu.ntnu.idi.idatt.model.factory.ResetActionFactory;
import edu.ntnu.idi.idatt.model.factory.TileActionFactoryRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class BoardFileReaderGsonTest {
    private Path tempFile;
    private TileActionFactoryRegistry registry;

    /**
     * Sets up a valid JSON file containing a board before each test.
     * Also sets up a tile with ladder and reset actions,
     * and registers the tile action factories.
     *
     * @throws IOException if an I/O error occurs.
     */
    @BeforeEach
    public void setup() throws IOException {
        String json = getJsonString();
        tempFile = Files.createTempFile("boardReader", ".json");
        Files.writeString(tempFile, json);
        registry = new TileActionFactoryRegistry();
        registry.registerDestinationFactory(new LadderActionFactory());
        registry.registerNoDestinationFactory(new ResetActionFactory());
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

    // ----- Positive tests -----
    /**
     * Test reading a valid board from a file.
     *
     * <p>Expected: The board's name, description and tiles
     * should be read correctly.</p>
     */
    @Test
    public void testReaderWithValidBoard() {
        BoardFileReaderGson reader = new BoardFileReaderGson(registry);
        Board board = reader.readBoard(tempFile);
        checkBoardMetadata(board);
        checkTilesAndNextTiles(board);
        checkTileActions(board);
    }

    /**
     * Checks if the read board has the correct name and description.
     *
     * @param board is the board to check.
     */
    private void checkBoardMetadata(Board board) {
        // Validate board meta data.
        assertEquals("Reader Test Board", board.getName());
        assertEquals("Board with action", board.getDescription());
    }

    /**
     * Checks if the read board has the correct tiles and next-tile relationships.
     *
     * @param board is the board to check.
     */
    private void checkTilesAndNextTiles(Board board) {
        assertEquals(3, board.getTiles().size());
        assertTrue(board.getTile(1).getNextTile().isPresent());
        assertEquals(2, board.getTile(1).getNextTile().get().getTileId());
        assertTrue(board.getTile(2).getNextTile().isPresent());
        assertEquals(3, board.getTile(2).getNextTile().get().getTileId());
        assertFalse(board.getTile(3).getNextTile().isPresent());
    }

    /**
     * Checks if the read board has the correct tile actions.
     *
     * @param board is the board to check.
     */
    private void checkTileActions(Board board) {
        // Validate that tile 1 has a LadderAction.
        assertTrue(board.getTile(1).getLandAction().isPresent());
        assertInstanceOf(LadderAction.class, board.getTile(1).getLandAction().get());
        // Validate that tile 3 has a ResetAction.
        assertTrue(board.getTile(3).getLandAction().isPresent());
        assertInstanceOf(ResetAction.class, board.getTile(3).getLandAction().get());
    }

    // ----- Negative tests -----

    /**
     * Test reading a board from a non-existing file.
     *
     * <p>Expected: An IOException should be thrown.</p>
     */
    @Test
    public void testReaderWithNullPath() {
        BoardFileReaderGson reader = new BoardFileReaderGson(registry);
        assertThrows(IllegalArgumentException.class, () -> reader.readBoard(null));
    }

    /**
     * Test reading a board from a file with invalid JSON.
     *
     * <p>Expected: A BoardParsingException should be thrown.</p>
     */
    @Test
    public void testReaderWithNullRegistry() {
        assertThrows(IllegalArgumentException.class, () -> new BoardFileReaderGson(null));
    }


    /**
     * Test reading a board with missing optional fields and no tiles.
     *
     * <p>Expected: The board should be read with default name and description,
     * and an empty map of tiles.</p>
     */
    @Test
    public void testReaderWithMissingAllOptionalFieldsAndNoTiles() throws IOException {
        String json = "{" + "\"tiles\": []" + "}";
        Files.writeString(tempFile, json);
        BoardFileReaderGson reader = new BoardFileReaderGson(registry);
        Board board = reader.readBoard(tempFile);

        assertEquals("Unnamed Board", board.getName());
        assertEquals("No description available.", board.getDescription());
        assertTrue(board.getTiles().isEmpty());
    }

    /**
     * Test reading a board from a file with invalid JSON.
     *
     * <p>Expected: A BoardParsingException should be thrown.</p>
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void testReaderWithInvalidJson() throws IOException {
        String invalidJson = getInvalidJsonString();
        Files.writeString(tempFile, invalidJson);
        BoardFileReaderGson reader = new BoardFileReaderGson(registry);
        assertThrows(BoardParsingException.class, () -> reader.readBoard(tempFile));
    }

    /**
     * Test reading a board from a non-existing file.
     *
     * <p>Expected: A BoardParsingException should be thrown.</p>
     */
    @Test
    public void testNonExistingFile() {
        BoardFileReaderGson reader = new BoardFileReaderGson(registry);
        assertThrows(BoardParsingException.class, () -> reader.readBoard(Path.of("non-existing-file.json")));
    }

    /**
     * Test reading a board that contains an action with an unregistered type.
     *
     * <p>Expected: A BoardParsingException should be thrown.</p>
     */
    @Test
    public void testReaderWithUnregisteredActionType() throws IOException {
        String json = getJsonStringWithNonRegisteredAction();
        Files.writeString(tempFile, json);
        BoardFileReaderGson reader = new BoardFileReaderGson(registry);
        assertThrows(BoardParsingException.class, () -> reader.readBoard(tempFile));
    }


    // ----- Helper methods -----
    /**
     * Returns a JSON string representing a board with actions.
     * Contains 3 tiles. Tile 1 has a ladder action, tile 3 has a reset action.
     *
     * @return a JSON string.
     *
     * <strong>GitHub Copilot generated the string.</strong>
     */
    private String getJsonString() {
        return "{"
                + "\"name\": \"Reader Test Board\","
                + "\"description\": \"Board with action\","
                + "\"tiles\": ["
                + "{\"id\": 1, \"nextTile\": 2, \"action\": {\"type\": \"LadderAction\", \"destinationTileId\": 2}},"
                + "{\"id\": 2, \"nextTile\": 3},"
                + "{\"id\": 3, \"action\": {\"type\": \"ResetAction\"}}"
                + "]"
                + "}";
    }

    /**
     * Returns an invalid JSON string that is missing a closing bracket.
     *
     * @return an invalid JSON string.
     *
     * <strong>GitHub Copilot generated the string.</strong>
     */
    private String getInvalidJsonString() {
        return "{ \"name\": \"Invalid Board\", \"tiles\": [ { \"id\": 1, \"nextTile\": 2 } ";
    }

    /**
     * Returns a JSON string representing a board with an action that has an unregistered type.
     *
     * @return a JSON string.
     *
     * <strong>GitHub Copilot generated the string.</strong>
     */
    private String getJsonStringWithNonRegisteredAction() {
        return "{"
                + "\"name\": \"Unregistered Action Board\","
                + "\"description\": \"Board with bad action\","
                + "\"tiles\": ["
                + "{\"id\": 1, \"action\": {\"type\": \"NonExistentAction\"}}"
                + "]"
                + "}";
    }
}
