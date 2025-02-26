package edu.ntnu.idi.idatt.model.actions;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the ReturnToStartAction class.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class ReturnToStartActionTest {
    Player player;
    Tile startTile;
    Tile tileWithAction;
    Tile normalTile;

    /**
     * Sets up a player who is placed on a no-action tile and
     * a tile with a return-to-start action.
     */
    @BeforeEach
    public void setUp() {
        startTile = new Tile(1);
        normalTile = new Tile(2);
        tileWithAction = new Tile(5);
        tileWithAction.setLandAction(new ReturnToStartAction());
        player = new Player("Player");
        player.placeOnTile(startTile); // First tile is the start tile
        player.placeOnTile(normalTile);
    }

    // ------- Positive tests -------

    /**
     * Tests that the player is moved back to the start tile.
     *
     * <p>Expected outcome: The player is placed on the start tile.</p>
     */
    @Test
    public void testValidReturnToStart() {
        assertEquals(normalTile, player.getCurrentTile());
        player.placeOnTile(tileWithAction);
        assertEquals(startTile, player.getCurrentTile());
    }

    // ------- Negative tests -------

    /**
     * Tests performing the return-to-start action with a null player.
     *
     * <p>Expected outcome: An IllegalArgumentException is thrown.</p>
     */
    @Test
    public void testPerformWithNullPlayer() {
        ReturnToStartAction action = new ReturnToStartAction();
        assertThrows(IllegalArgumentException.class, () -> action.perform(null));
    }
}
