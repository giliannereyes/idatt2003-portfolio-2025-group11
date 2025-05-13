package edu.ntnu.idi.idatt.domain.entity;

import edu.ntnu.idi.idatt.domain.action.snakesandladders.LadderAction;
import edu.ntnu.idi.idatt.domain.action.TileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for {@link Board}.
 * Tests cover board initialisation, add and retrieving tiles, and player-tile-relationship.
 *
 * @author Trang Duong
 * @version 0.3
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
        board = new Board(2,2);
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
        Tile tile = new Tile(1, 1, 1);
        board.addTile(tile);
        assertEquals(tile, board.getTile(1),
                "Tile should be retrieved from the board"
        );
    }

    /**
     * Tests if player is placed on a start tile correctly.
     *
     * <p>Expected: The player should be placed on the start tile.</p>
     */
    @Test
    void testPlacePlayerOnStartTile() {
        Tile startTile = new Tile(1, 0, 0);
        board.addTile(startTile);
        board.placePlayerOnStartTile(player);
        assertEquals(startTile.getTileId(), player.getCurrentTile().getTileId(),
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
        Tile tileWithAction = new Tile(1, 0, 0);
        Tile destinationTile = new Tile(2, 1, 1);
        board.addTile(tileWithAction);
        board.addTile(destinationTile);
        TileAction expectedAction = new LadderAction(destinationTile);
        board.addTileAction(tileWithAction.getTileId(), expectedAction);
        assertTrue(board.getTile(tileWithAction.getTileId())
                        .getLandAction().isPresent(),
                "Tile action should be added to the tile"
        );
        TileAction actualAction = board.getTile(tileWithAction.getTileId())
                .getLandAction().get();
        assertEquals(expectedAction, actualAction,
                "Tile action should be the same as the added action"
        );
    }

    /**
     * Tests if the board name and description can be changed.
     *
     * <p>Expected: The name and description of the board should be changed.</p>
     */
    @Test
    void testSetNewNameAndDescription() {
        assertEquals("Unnamed Board", board.getName());
        assertEquals("No description available.", board.getDescription());
        board.setName("Test Board");
        board.setDescription("Test Description");
        assertEquals("Test Board", board.getName());
        assertEquals("Test Description", board.getDescription());
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
        Tile tile = new Tile(1, 0, 0);
        board.addTile(tile);
        assertThrows(IllegalArgumentException.class,
                () -> board.addTileAction(tile.getTileId(), null),
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

    /**
     * Tests if the tiles of the board can be modified outside its methods.
     *
     * <p>Expected: An {@link UnsupportedOperationException} should be thrown.</p>
     */
    @Test
    void testModifyBoardTiles() {
        board.addTile(new Tile(1, 0, 0));
        Map<Integer, Tile> tiles = board.getTiles();
        assertThrows(UnsupportedOperationException.class ,
                () -> tiles.put(1, new Tile(5,4,0)));
    }
}
