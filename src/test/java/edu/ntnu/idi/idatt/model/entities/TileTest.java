package edu.ntnu.idi.idatt.model.entities;

import edu.ntnu.idi.idatt.model.actions.LadderAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * JUnit 5 test class for {@link Player} and {@link Tile}.
 * Tests cover tile initialisation and player action on tile.
 *
 * @author Trang Duong
 * @version 0.3
 * @since 0.1
 */
public class TileTest {
    private Tile tile;
    private Player player;

    /**
     * Initialises a new Player and Tile instance before each test.
     */
    @BeforeEach
    void setUp() {
        tile = new Tile(1,1,1);
        player = new Player("Ola");
    }

    // --------- Positive tests ---------

    /**
     * Tests if a tile is initialised correctly.
     *
     * <p> Expected outcome: The tile has the correct id, is the last tile, and has no next tile.</p>
     */
    @Test
    void testTileInitialisation() {
        assertEquals(1, tile.getTileId());
        assertTrue(tile.isLastTile());
        assertTrue(tile.getNextTile().isEmpty());
    }

    /**
     * Tests if a player can land on a tile without an action.
     *
     * <p> Expected outcome: A player lands on the tile without any exceptions thrown.</p>
     */
    @Test
    void landPlayerWithoutAction() {
        assertDoesNotThrow(() -> tile.landPlayer(player));
    }

    /**
     * Tests if a player can land on a tile with an action. The action places
     * the player on another tile.
     *
     * <p> Expected outcome: The action is performed and the player is placed on the new tile.</p>
     */
    @Test
    void landPlayerWithAction() {
        Tile tile2 = new Tile(2,2,1);
        tile.setLandAction(new LadderAction(tile2));
        tile.landPlayer(player);
        assertEquals(tile2, player.getCurrentTile());
    }

    /**
     * Tests if the next tile is correctly set and retrieved.
     *
     * <p> Expected outcome: The next tile is set and retrieved correctly.</p>
     */
    @Test
    void testSetAndGetNextTile() {
        Tile nextTile = new Tile(2,2,1);
        tile.setNextTile(nextTile);
        Optional<Tile> retrievedNextTile = tile.getNextTile();
        assertTrue(retrievedNextTile.isPresent());
        assertEquals(nextTile, retrievedNextTile.get());
        assertFalse(tile.isLastTile());
    }

    // --------- Negative tests ---------

    /**
     * Tests if a tile is initialised with a negative id.
     *
     * <p>Expected outcome: An {@link IllegalArgumentException} is thrown.</p>
     */
    @Test
    void testTileInitialisationWithNegativeId() {
        assertThrows(IllegalArgumentException.class, () -> new Tile(-1,1,1));
    }

    /**
     * Tests if a player with a null value can land on a tile.
     *
     * <p>Expected outcome: An {@link IllegalArgumentException} is thrown.</p>
     */
    @Test
    void landNullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> tile.landPlayer(null));
    }
}

