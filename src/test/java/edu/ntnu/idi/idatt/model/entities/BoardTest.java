package edu.ntnu.idi.idatt.model.entities;

import edu.ntnu.idi.idatt.model.actions.LadderAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for {@link Board}.
 * Tests cover board initialisation, add and retrieving tiles, and player-tile-relationship.
 *
 * @author Trang Duong
 * @version 0.2
 * @since 0.1
 */
public class BoardTest {
    private Board board;
    private Player player;

    /**
     * Sets up the board and player for each test.
     */
    @BeforeEach
    public void setUp() {
        board = new Board();
        player = new Player("TestPlayer");
    }

    // ------- Positive tests -------

    /**
     * Tests if add tile works successfully and if the added tile is retrievable.
     *
     * <p>Expected: The tile should be added to the board and is retrievable.</p>
     */
    @Test
    public void testAddTile() {
        Tile tile = new Tile(1);
        board.addTile(tile);
        assertEquals(tile, board.getTile(1),
                "Tile should be retrieved from the board"
        );
    }

    /**
     * Tests that board is initialised within a given range of tiles.
     *
     * <p>Expected: The board should be initialized with the given number of tiles.</p>
     */
    @Test
    void testInitializeBoard() {
        board.initializeBoard(5);
        assertNotNull(board.getTile(1),
                "First tile should exist after initialization"
        );
        assertNotNull(board.getTile(5),
                "Last tile should exist after initialization"
        );
        assertNull(board.getTile(6),
                "Tile outside initialized range should not exist"
        );
    }

    /**
     * Tests if player is placed on a start tile correctly.
     *
     * <p>Expected: The player should be placed on the start tile.</p>
     */
    @Test
    void testPlacePlayerOnStartTile() {
        board.placePlayerOnStartTile(player);
        assertEquals(0, player.getCurrentTile().getTileId(),
                "Player should be placed on the start tile 0"
        );
    }

    /**
     * Tests if a tile action can be added to a tile.
     *
     * <p>Expected: The tile action should be added to the tile.</p>
     */
    @Test
    void testAddTileAction() {
        board.initializeBoard(5);
        TileAction expectedAction = new LadderAction(board.getTile(3));
        board.addTileAction(1, expectedAction);
        assertTrue(board.getTile(1).getLandAction().isPresent(),
                "Tile action should be added to the tile"
        );
        TileAction actualAction = board.getTile(1).getLandAction().get();
        assertEquals(expectedAction, actualAction,
                "Tile action should be the same as the added action"
        );
    }

    // ------- Negative tests -------

    /**
     * Tests if an exception is thrown when adding a null tile.
     *
     * <p>Expected: An {@link IllegalArgumentException} should be thrown.</p>
     */
    @Test
    void testAddNullTile() {
        assertThrows(IllegalArgumentException.class,
                () -> board.addTile(null),
                "Null tile should not be added to the board"
        );
    }

    /**
     * Tests if the board can be initialised with a negative tile count.
     *
     * <p>Expected: An {@link IllegalArgumentException} should be thrown.</p>
     */
    @Test
    void testInitializeBoardWithNegativeTileCount() {
        assertThrows(IllegalArgumentException.class,
                () -> board.initializeBoard(-1),
                "Negative tile count should throw an exception"
        );
    }

    /**
     * Tests if a null player can be placed on the start tile.
     *
     * <p>Expected: An {@link IllegalArgumentException} should be thrown.</p>
     */
    @Test
    void testPlaceNullPlayerOnStartTile() {
        assertThrows(IllegalArgumentException.class,
                () -> board.placePlayerOnStartTile(null),
                "Null player should not be placed on the board"
        );
    }

    /**
     * Tests if a null tile action can be added to a tile.
     *
     * <p>Expected: An {@link IllegalArgumentException} should be thrown.</p>
     */
    @Test
    void testAddNullTileAction() {
        board.initializeBoard(5);
        assertThrows(IllegalArgumentException.class,
                () -> board.addTileAction(1, null),
                "Null tile action should not be added to the tile"
        );
    }

    /**
     * Tests if a tile action can be added to a non-existing tile.
     *
     * <p>Expected: An {@link IllegalArgumentException} should be thrown.</p>
     */
    @Test
    void testAddTileActionToNonExistingTile() {
        assertThrows(IllegalArgumentException.class,
                () -> board.addTileAction(1, new LadderAction(board.getTile(3)))
        );
    }
}
