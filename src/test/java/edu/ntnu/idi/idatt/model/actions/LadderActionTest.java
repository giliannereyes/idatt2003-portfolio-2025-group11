package edu.ntnu.idi.idatt.model.actions;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for {@link LadderAction}.
 * Tests cover tile and player initialisation and ladder action.
 *
 * @author Trang Duong
 * @version 0.1
 * @since 0.1
 */
public class LadderActionTest {
    private Tile startTile;
    private Tile destinationTile;
    private Player player;
    private LadderAction ladderAction;

    /**
     * Initialises a new Player, a start and destination Tile, and a LandAction instance before each test.
     * Player is manually placed on the start tile
     */
    @BeforeEach
    public void setUp() {
        startTile = new Tile(1); //Assume tileID
        destinationTile = new Tile(10);
        player = new Player("TestPlayer");
        ladderAction = new LadderAction(destinationTile);

        player.placeOnTile(startTile);
    }

    /**
     * Tests if ladder action is executed properly and player lands on correct destination tile.
     */
    @Test
    public void testLadderAction() {
        assertEquals(startTile, player.getCurrentTile());
        ladderAction.perform(player);
        assertEquals(destinationTile, player.getCurrentTile(), "Player should have moved to the destination tile");
    }
}
