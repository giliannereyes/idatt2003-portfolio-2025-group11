package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.SnakeAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for the {@link SnakeActionFactory} class.
 *  This test ensures that the factory correctly creates a {@link SnakeAction}
 *  and that it performs the expected behavior when executed.
 *
 * @author Trang Duong
 * @version 0.1
 * @since 0.1
 */
public class SnakeActionFactoryTest {
    private Tile destinationTile;
    private SnakeActionFactory snakeActionFactory;

    /**
     * Initialises a destination tile and a {@link SnakeActionFactory} instance
     */
    @BeforeEach
    public void setUp() {
        destinationTile = new Tile(10);
        snakeActionFactory = new SnakeActionFactory();
    }

    /**
     * Tests whether the {@link SnakeActionFactory} correctly creates a {@link SnakeAction}.
     * The test checks that the created action is an instance of {@link SnakeAction} and
     * the created action correctly moves the player to the destination tile.
     */
    @Test
    public void testCreateLadderTileAction() {
        TileAction action = snakeActionFactory.createTileAction(destinationTile);

        assertNotNull(action, "Ladder action should not be null");
        assertInstanceOf(SnakeAction.class, action, "The factory should return an instance of SnakeAction");

        Player player = new Player("TestPlayer");
        action.perform(player);

        assertEquals(destinationTile, player.getCurrentTile(), "Player should have moved to the destination tile");
    }
}