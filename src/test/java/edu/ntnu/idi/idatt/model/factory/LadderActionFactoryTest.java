package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.LadderAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for the {@link LadderActionFactory} class.
 *  This test ensures that the factory correctly creates a {@link LadderAction}
 *  and that it performs the expected behavior when executed.
 *
 * @author Trang Duong
 * @version 0.1
 * @since 0.1
 */
public class LadderActionFactoryTest {
    private Tile destinationTile;
    private LadderActionFactory ladderActionFactory;

    /**
     * Initialises a destination tile and a {@link LadderActionFactory} instance
     */
    @BeforeEach
    public void setUp() {
        destinationTile = new Tile(10);
        ladderActionFactory = new LadderActionFactory();
    }

    /**
     * Tests whether the {@link LadderActionFactory} correctly creates a {@link LadderAction}.
     * The test checks that the created action is an instance of {@link LadderAction} and
     * the created action correctly moves the player to the destination tile.
     */
    @Test
    public void testCreateLadderTileAction() {
        TileAction action = ladderActionFactory.createTileAction(destinationTile);

        assertNotNull(action, "Ladder action should not be null");
        assertInstanceOf(LadderAction.class, action, "The factory should return an instance of LadderAction");

        Player player = new Player("TestPlayer");
        action.perform(player);

        assertEquals(destinationTile, player.getCurrentTile(), "Player should have moved to the destination tile");
    }

    /**
     * Tests the instance when Player is null
     */
    @Test
    public void testLadderActionPerformWithNullPlayer() {
        TileAction action = ladderActionFactory.createTileAction(destinationTile);

        assertThrows(NullPointerException.class, () -> {
            action.perform(null);
        }, "Calling perform() on a null player should throw a NullPointerException.");
    }

    /**
     * Ensures that the instances are independent
     */
    @Test
    public void testLadderActionFactoryCreatesNewInstances() {
        TileAction action1 = ladderActionFactory.createTileAction(destinationTile);
        TileAction action2 = ladderActionFactory.createTileAction(destinationTile);

        assertNotSame(action1, action2, "Factory should return a new instance each time.");
    }
}