package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.SkipTurnAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for the {@link SkipTurnAction} class.
 *  This test ensures that the factory correctly creates a {@link SkipTurnAction}
 *  and that it performs the expected behavior when executed.
 *
 * @author Trang Duong
 * @version 0.1
 * @since 0.1
 */
public class SkipTurnActionFactoryTest {
    private Tile destinationTile;
    private SkipTurnActionFactory skipTurnActionFactory;

    /**
     * Initialises a destination tile and a {@link SkipTurnActionFactory} instance
     */
    @BeforeEach
    public void setUp() {
        destinationTile = new Tile(10);
        skipTurnActionFactory = new SkipTurnActionFactory();
    }

    /**
     * Tests whether the {@link SkipTurnActionFactory} correctly creates a {@link SkipTurnAction}.
     * The test checks that the created action is an instance of {@link SkipTurnAction} and
     * the created action correctly moves the player to the destination tile.
     */
    @Test
    public void testCreateSkipTurnTileAction() {
        TileAction action = skipTurnActionFactory.createTileAction(destinationTile);

        assertNotNull(action, "Ladder action should not be null");
        assertInstanceOf(SkipTurnAction.class, action, "The factory should return an instance of SnakeAction");

        Player player = new Player("TestPlayer");
        action.perform(player);

        assertEquals(destinationTile, player.getCurrentTile(), "Player should have moved to the destination tile");
    }

    /*
    @Test
    public void testSkipTurnEffect() {
        Player player = new Player("TestPlayer");
        TileAction action = skipTurnActionFactory.createTileAction(destinationTile);

        action.perform(player);

        assertTrue(player.isTurnSkipped(), "Player's turn should be skipped after performing SkipTurnAction");
    }
    */

    /**
     * Tests the instance when Player is null
     */
    @Test
    public void testSkipTurnActionPerformWithNullPlayer() {
        TileAction action = skipTurnActionFactory.createTileAction(destinationTile);

        assertThrows(NullPointerException.class, () -> {
            action.perform(null);
        }, "Calling perform() on a null player should throw a NullPointerException.");
    }

    /**
     * Ensures that the instances are independent
     */
    @Test
    public void testSkipTurnFactoryCreatesNewInstances() {
        TileAction action1 = skipTurnActionFactory.createTileAction(destinationTile);
        TileAction action2 = skipTurnActionFactory.createTileAction(destinationTile);

        assertNotSame(action1, action2, "Factory should return a new instance each time.");
    }
}