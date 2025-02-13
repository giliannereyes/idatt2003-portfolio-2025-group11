package edu.ntnu.idi.idatt.model.entities;

import edu.ntnu.idi.idatt.model.actions.TileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for {@link Board}.
 * Tests cover board initialisation, add and retrieving tiles, and player-tile-relationship.
 *
 * @author Trang Duong
 * @version 0.1
 * @since 0.1
 */
public class BoardTest {
    private Board board;
    private Player player;

    @BeforeEach
    public void setUp() {
        board = new Board();
        player = new Player("TestPlayer");
    }

    /**
     * Tests if add tile works successfully and if the added tile is retrievable
     */
    @Test
    public void testAddTile() {
        Tile tile = new Tile(1);
        board.addTile(tile);

        assertEquals(tile, board.getTile(1), "Tile should be retrieved from the board");
    }

    /**
     * Tests that board is initialised within a given range
     */
    @Test
    void testInitializeBoard() {
        board.initializeBoard(5);

        assertNotNull(board.getTile(1), "First tile should exist after initialization");
        assertNotNull(board.getTile(5), "Last tile should exist after initialization");
        assertNull(board.getTile(6), "Tile outside initialized range should not exist");
    }

    /**
     * Tests if player is placed on a start tile correctly
     */
    @Test
    void testPlacePlayerOnStartTile() {
        board.placePlayerOnStartTile(player);
        assertEquals(0, player.getCurrentTile().getTileId(), "Player should be placed on the start tile 0");
    }

    /* @Test
    void testAddTileAction() {
        TileAction mockAction = () -> System.out.println("Action triggered");
        board.initializeBoard(3);
        board.addTileAction(2, mockAction);
        assertNotNull(board.getTile(2).getLandAction(), "Tile 2 should have an action set");
    }

     */
}
