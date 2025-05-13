package edu.ntnu.idi.idatt.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for {@link Player}.
 * Tests cover player initialisation, and movement.
 *
 * @author Trang Duong
 * @author Gilianne Reyes
 * @version 0.3
 * @since 0.1
 */
public class PlayerTest {
    private Player player;
    private Tile startTile;
    private Tile nextTile;

    /**
     * Initialises a new Player instance before each test.
     */
    @BeforeEach
    void setUp() {
        player = new Player("Ola");
        startTile = new Tile(1, 1, 1);
        nextTile = new Tile(2, 2, 2);
        startTile.setNextTile(nextTile);
        player.placeOnTile(startTile);
    }

    // -------- Positive tests --------

    /**
     * Tests if a player is correctly initialised with the expected name and position.
     *
     * <p> Expected outcome: The player has the correct name and is placed on the start tile.</p>
     */
    @Test
    void testPlayerInitialisation() {
        assertEquals("Ola", player.getName());
        assertEquals(startTile, player.getCurrentTile());
    }

    /**
     * Tests if the player moves correctly to a new position
     * with the default movement strategy.
     *
     * <p> Expected outcome: The player is placed on the next tile.</p>
     */
    @Test
    void testMovePlayerWithDefaultStrategy() {
        player.move(1);
        assertEquals(nextTile, player.getCurrentTile());
    }

    /**
     * Tests if the player is placed on expected tile.
     *
     * <p> Expected outcome: The player is placed on the next tile.</p>
     */
    @Test
    void testPlaceOnTile() {
        player.placeOnTile(nextTile);
        assertEquals(nextTile, player.getCurrentTile());
    }

    /**
     * Tests if the skip-turn flag is set to true.
     *
     * <p> Expected outcome: The skip-turn flag is set to true.</p>
     */
    @Test
    void testSetSkipTurnToTrue() {
        player.setSkipTurn(true);
        assertTrue(player.willSkipTurn());
    }

    /**
     * Tests if a player's first tile is registered as the start tile.
     */
    @Test
    void testCorrectStartTile() {
        assertEquals(startTile, player.getStartTile());
    }

    // -------- Negative tests --------
    /**
     * Tests if a player is initialised with an empty name.
     *
     * <p> Expected outcome: IllegalArgumentException is thrown.</p>
     */
    @Test
    void testInitialisePlayerWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Player(""));
    }

    /**
     * Tests if a player is initialised with a null name.
     *
     * <p> Expected outcome: IllegalArgumentException is thrown.</p>
     */
    @Test
    void testInitialisePlayerWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new Player(null));
    }

    /**
     * Tests if a player can have null as the movement strategy.
     *
     * <p> Expected outcome: IllegalArgumentException is thrown.</p>
     */
    @Test
    void testSetMovementStrategyWithNull() {
        assertThrows(IllegalArgumentException.class, () -> player.setMovementStrategy(null));
    }
}
