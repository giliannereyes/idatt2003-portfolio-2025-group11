package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.model.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for {@link Player}.
 * Tests cover player initialisation, movement, and turn-skipping behavior.
 *
 * @author Trang Duong
 * @version 0.1
 * @since 0.1
 */
public class PlayerTest {

    private Player player; //Declare player

    /**
     * Initialises a new Player instance before each test.
     */
    @BeforeEach
    void setUp() {
        player = new Player("Ola", 0); // New player before each test
    }

    /**
     * Tests if a player is correctly initialised with the expected name and position.
     */
    @Test
    void testPlayerInitialisation() {
        assertEquals("Ola", player.getName());
        assertEquals(0, player.getPosition());
    }

    /**
     * Tests if the player moves correctly to a new position.
     */
    @Test
    void testMovePlayer() {
        player.move(10);
        assertEquals(10, player.getPosition());
    }

    /**
     * Tests if the 'skip next turn' flag is correctly set when activated.
     */
    @Test
    void testSkipNextTurnEffect() {
        player.setSkipNextTurn(true);
        assertTrue(player.shouldSkipNextTurn());
    }

    /**
     * Tests if the 'skip next turn' flag can be reset after a skipped turn.
     */
    @Test
    void testResetSkipNextTurn() {
        player.setSkipNextTurn(true);
        player.setSkipNextTurn(false);
        assertFalse(player.shouldSkipNextTurn(), "Player should be able to play again after skipping a turn");
    }
}
