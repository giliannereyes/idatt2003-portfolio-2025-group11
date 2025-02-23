package edu.ntnu.idi.idatt.model.actions;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SkipTurnActionTest {
    private Tile tile;
    private Player player;
    private SkipTurnAction skipTurnAction;

    /**
     * Sets up a player, a destination tile, and a SkipTurnAction instance before each test.
     */
    @BeforeEach
    void setUp() {
        tile = new Tile(5);
        player = new Player("TestPlayer");
        skipTurnAction = new SkipTurnAction(tile);
    }

    /**
     * Ensures that if the player is already on destination tile, no unintended behavior occurs.
     */
    @Test
    void testSkipTurnAction() {
        player.placeOnTile(tile);

        skipTurnAction.perform(player);

        assertEquals(tile, player.getCurrentTile(), "Player should remain on the same tile.");
    }

    /**
     * Ensures that calling action on a null player throws an exception.
     */
    @Test
    void testSkipTurnActionWithNullPlayer() {
        assertThrows(NullPointerException.class, () -> {
            skipTurnAction.perform(null);
        }, "Calling action on a null player should throw a NullPointerException.");
    }
}

