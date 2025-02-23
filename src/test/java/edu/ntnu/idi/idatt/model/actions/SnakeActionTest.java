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
 * @version 0.2
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

    /**
     * Tests if there is no Player
     */
    @Test
    public void testLadderActionWithNullPlayer() {
        assertThrows(NullPointerException.class, () -> {
            snakeAction.perform(null);
        }, "Performing ladder action on a null player should throw an exception");
    }

    /**
     * Tests if Player is already on destination tile
     */
    @Test
    public void testLadderActionWhenPlayerAlreadyOnDestination() {
        player.placeOnTile(destinationTile);
        snakeAction.perform(player);
        assertEquals(destinationTile, player.getCurrentTile(), "Player should stay on the destination tile");
    }

    /**
     * Tests if Player is not initially placed on any tile
     */
    @Test
    public void testLadderActionWhenPlayerNotPlacedOnAnyTile() {
        Player newPlayer = new Player("UnplacedPlayer");
        snakeAction.perform(newPlayer);
        assertEquals(destinationTile, newPlayer.getCurrentTile(), "Player should be moved to the destination tile even if not initially placed");
    }
}

