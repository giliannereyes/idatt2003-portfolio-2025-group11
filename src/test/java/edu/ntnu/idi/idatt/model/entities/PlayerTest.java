package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for {@link Player}.
 * Tests cover player initialisation, and movement.
 *
 * @author Trang Duong
 * @version 0.2
 * @since 0.1
 */
public class PlayerTest {

    private Player player;
    private Tile startTile;
    private Tile endTile;

    /**
     * Initialises a new Player instance before each test.
     */
    @BeforeEach
    void setUp() {
        player = new Player("Ola");
        startTile = new Tile(1);
        endTile = new Tile(2);

        startTile.setNextTile(endTile);
        player.placeOnTile(startTile);
    }

    /**
     * Tests if a player is correctly initialised with the expected name and position.
     */
    @Test
    void testPlayerInitialisation() {
        assertEquals("Ola", player.getName());
        assertEquals(startTile, player.getCurrentTile());
    }

    /**
     * Tests if the player moves correctly to a new position
     * with the defaulted movement strategy.
     */
    @Test
    void testMovePlayerWithDefaultStrategy() {
        player.move(1);
        assertEquals(endTile, player.getCurrentTile());
    }

    /** Tests if the player is placed on expected tile
     */
    @Test
    void testPlaceOnTile() {
        player.placeOnTile(endTile);
        assertEquals(endTile, player.getCurrentTile());
    }
}
