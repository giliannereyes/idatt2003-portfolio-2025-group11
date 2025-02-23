package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.StepBackAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for the {@link StepBackAction} class.
 *  This test ensures that the factory correctly creates a {@link StepBackAction}
 *  and that it performs the expected behavior when executed.
 *
 * @author Trang Duong
 * @version 0.1
 * @since 0.1
 */
public class StepBackActionFactoryTest {
    private Tile destinationTile;
    private StepBackActionFactory stepBackActionFactory;

    /**
     * Initialises a destination tile and a {@link StepBackActionFactory} instance
     */
    @BeforeEach
    public void setUp() {
        destinationTile = new Tile(10);
        stepBackActionFactory = new StepBackActionFactory();
    }

    /**
     * Tests whether the {@link StepBackActionFactory} correctly creates a {@link StepBackAction}.
     * The test checks that the created action is an instance of {@link StepBackAction} and
     * the created action correctly moves the player to the destination tile.
     */
    @Test
    public void testCreateStepBackTileAction() {
        TileAction action = stepBackActionFactory.createTileAction(destinationTile);

        assertNotNull(action, "Ladder action should not be null");
        assertInstanceOf(StepBackAction.class, action, "The factory should return an instance of SnakeAction");

        Player player = new Player("TestPlayer");
        action.perform(player);

        assertEquals(destinationTile, player.getCurrentTile(), "Player should have moved to the destination tile");
    }

    /**
     * Tests the instance when Player is null
     */
    @Test
    public void testStepBackActionPerformWithNullPlayer() {
        TileAction action = stepBackActionFactory.createTileAction(destinationTile);

        assertThrows(NullPointerException.class, () -> {
            action.perform(null);
        }, "Calling perform() on a null player should throw a NullPointerException.");
    }

    /**
     * Ensures that the instances are independent
     */
    @Test
    public void testStepBackActionFactoryCreatesNewInstances() {
        TileAction action1 = stepBackActionFactory.createTileAction(destinationTile);
        TileAction action2 = stepBackActionFactory.createTileAction(destinationTile);

        assertNotSame(action1, action2, "Factory should return a new instance each time.");
    }
}