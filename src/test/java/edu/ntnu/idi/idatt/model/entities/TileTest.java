package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for {@link Player} and {@link Tile}.
 * Tests cover tile initialisation and player action on tile.
 *
 * @author Trang Duong
 * @version 0.1
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
        tile = new Tile(1);
        player = new Player("Ola");
    }

    /**
     * Tests if a tile is initialised correctly.
     */
    @Test
    void testTileInitialisation() {
        assertEquals(1, tile.getTileId());
        assertTrue(tile.isLastTile());
        assertNull(tile.getNextTile());
    }

    /**
     * Tests if the next tile is correctly set and retrieved.
     */
    @Test
    void testSetAndGetNextTile() {
        Tile nextTile = new Tile(2);

        tile.setNextTile(nextTile);

        assertEquals(nextTile, tile.getNextTile());
        assertFalse(tile.isLastTile());
    }

    /**
     * Ensures that landing on a tile without an action does not cause an error.
     */
    @Test
    void landPlayerWithoutAction() {
        Tile tile = new Tile(1);
        Player player = new Player("TestPlayer");
        assertDoesNotThrow(() -> tile.landPlayer(player));
    }

    /**
     * Ensures that the action set on a tile is executed when a player lands on it.
     */
    @Test
    void landPlayerWithAction() {
        Tile tile = new Tile(1);
        Player player = new Player("TestPlayer");

        tile.setLandAction(new TileAction() {
            @Override
            public void perform(Player player) {
                player.placeOnTile(tile);
            }
        });
        tile.landPlayer(player);
        assertEquals(tile,player.getCurrentTile());
    }
}

