package edu.ntnu.idi.idatt.model.actions;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for {@link SnakeAction}.
 * Tests cover tile and player initialisation and snake action.
 *
 * @author Trang Duong
 * @version 0.1
 * @since 0.1
 */
public class SnakeActionTest {
    private Player player;
    private Tile startTile;
    private Tile destinationTile;
    private SnakeAction snakeAction;

    /**
     * Initialises a new Player, a start and destination Tile, and a SnakeAction instance before each test.
     * Player is manually placed on the start tile
     */
    @BeforeEach
    public void setUp() {
        player = new Player("TestPlayer");
        startTile = new Tile(10);
        destinationTile = new Tile(1);
        snakeAction = new SnakeAction(destinationTile);

        player.placeOnTile(startTile);
    }

    /**
     * Tests if snake action is executed properly and player lands on correct destination tile.
     */
    @Test
    public void testSnakeAction() {
        assertEquals(startTile, player.getCurrentTile());
        snakeAction.perform(player);
        assertEquals(destinationTile, player.getCurrentTile(), "Player should have moved to destination tile");
    }
}

