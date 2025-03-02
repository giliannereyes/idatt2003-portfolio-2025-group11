package edu.ntnu.idi.idatt.model.actions;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link SkipTurnAction} class.
 *
 * @version 0.2
 * @since 0.1
 * @author Trang Duong
 * @author Gilianne Reyes
 */
public class SkipTurnActionTest {
    private Tile tileWithSkipAction;
    private Player player;
    private SkipTurnAction skipTurnAction;

    /**
     * Sets up a player and a tile with SkipTurnAction before each test.
     */
    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer");
        skipTurnAction = new SkipTurnAction();
        tileWithSkipAction = new Tile(1);
        tileWithSkipAction.setLandAction(skipTurnAction);
    }

    // ------- Positive tests -------

    /**
     * Ensures that the player will skip their turn after the action is performed.
     *
     * <p>Expected: The player will skip their next turn.</p>
     */
    @Test
    void testSkipTurnAction() {
        tileWithSkipAction.landPlayer(player);
        assertTrue(player.willSkipTurn());
    }

    // ------- Negative tests -------

    /**
     * Ensures that calling action on a null player throws an exception.
     *
     * <p>Expected: IllegalArgumentException is thrown.</p>
     */
    @Test
    void testSkipTurnActionWithNullPlayer() {
        assertThrows(IllegalArgumentException.class,
                () -> skipTurnAction.perform(null),
                "Calling action on a null player should throw a NullPointerException."
        );
    }
}

