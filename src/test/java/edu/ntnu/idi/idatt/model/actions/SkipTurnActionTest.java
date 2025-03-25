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
        tileWithSkipAction = new Tile(1,0,0);
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

    /**
     * Test getting the action type of the skip turn action.
     *
     * <p>Expected: The action type retrieved should be alike
     * to the static actionType of the skip turn action.</p>
     */
    @Test
    public void testGetActionType() {
        assertEquals(SkipTurnAction.actionType, skipTurnAction.getActionType());
    }

    /**
     * Test getting the destination tile of the skip turn action.
     *
     * <p>Expected: The destination tile should be empty as skipping
     * turns require no destination tile.</p>
     */
    @Test
    public void testGetDestinationTile() {
        assertTrue(skipTurnAction.getDestinationTile().isEmpty());
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

