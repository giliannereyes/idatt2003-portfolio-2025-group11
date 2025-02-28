package edu.ntnu.idi.idatt.model.actions;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit 5 test class for {@link StepBackAction}.
 * Tests cover tile and player initialisation and step-back action.
 *
 * @author Trang Duong
 * @version 0.1
 * @since 0.1
 */
public class StepBackActionTest {
    private Player player;
    private Tile startTile;
    private Tile destinationTile;
    private StepBackAction stepBackAction;

    /**
     * Initialises a new Player, a start and destination Tile, and a StepBackAction instance before each test.
     * Player is manually placed on the start tile
     */
    @BeforeEach
    public void setUp() {
        player = new Player("TestPlayer");
        startTile = new Tile(10);
        destinationTile = new Tile(9);
        stepBackAction = new StepBackAction(destinationTile);

        player.placeOnTile(startTile);
    }

    /**
     * Tests if step-back action is executed properly and player lands on correct destination tile.
     */
    @Test
    public void testStepBackAction() {
        assertEquals(startTile, player.getCurrentTile());
        stepBackAction.perform(player);
        assertEquals(destinationTile, player.getCurrentTile(), "Player should have moved to destination tile");
    }

    /**
     * Ensures that calling action on a null player throws an exception.
     */
    @Test
    public void testStepBackActionWithNullPlayer() {
        assertThrows(NullPointerException.class, () -> {
            stepBackAction.perform(null);
        }, "Performing ladder action on a null player should throw an exception");
    }

    /**
     * Tests if Player is already on destination tile
     */
    @Test
    public void testStepBackActionWhenPlayerAlreadyOnDestination() {
        player.placeOnTile(destinationTile);
        stepBackAction.perform(player);
        assertEquals(destinationTile, player.getCurrentTile(), "Player should stay on the destination tile");
    }

    /**
     * Tests if Player is not initially placed on any tile
     */
    @Test
    public void testStepBackActionWhenPlayerNotPlacedOnAnyTile() {
        Player newPlayer = new Player("UnplacedPlayer");
        stepBackAction.perform(newPlayer);
        assertEquals(destinationTile, newPlayer.getCurrentTile(), "Player should be moved to the destination tile even if not initially placed");
    }
}