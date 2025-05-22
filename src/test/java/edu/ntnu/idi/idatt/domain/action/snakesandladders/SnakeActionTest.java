package edu.ntnu.idi.idatt.domain.action.snakesandladders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for {@link SnakeAction}.
 * Tests cover tile and player initialisation and snake action.
 *
 * @author Trang Duong
 * @version 0.3
 * @since 0.1
 */
public class SnakeActionTest {
  private Player player;
  private Tile startTile;
  private Tile destinationTile;
  private SnakeAction snakeAction;

  /**
   * Initialises a new Player, a start and destination Tile, a
   * nd a SnakeAction instance before each test. Player is manually placed on the start tile
   */
  @BeforeEach
  public void setUp() {
    player = new Player("TestPlayer");
    startTile = new Tile(10, 4, 1);
    destinationTile = new Tile(1, 0, 0);
    snakeAction = new SnakeAction(destinationTile);
    player.placeOnTile(startTile);
  }

  // ------------- Positive tests -------------

  /**
   * Tests if snake action is executed properly and player lands on correct destination tile.
   *
   * <p>Expected: Player should have moved to destination tile </p>
   */
  @Test
  public void testValidSnakeAction() {
    assertEquals(startTile, player.getCurrentTile());
    snakeAction.perform(player);
    assertEquals(destinationTile, player.getCurrentTile(),
          "Player should have moved to destination tile"
    );
  }

  /**
   * Tests SnakeAction when player is already on destination tile.
   *
   * <p>Expected: Player should stay on the destination tile (no exception). </p>
   */
  @Test
  public void testSnakeActionWhenPlayerAlreadyOnDestination() {
    player.placeOnTile(destinationTile);
    snakeAction.perform(player);
    assertEquals(destinationTile, player.getCurrentTile(),
          "Player should stay on the destination tile"
    );
  }

  /**
   * Tests if Player is not initially placed on any tile.
   *
   * <p>Expected: Player should be moved to the destination tile even if not initially placed. </p>
   */
  @Test
  public void testSnakeActionWhenPlayerNotPlacedOnAnyTile() {
    Player newPlayer = new Player("UnplacedPlayer");
    snakeAction.perform(newPlayer);
    assertEquals(destinationTile, newPlayer.getCurrentTile(),
          "Player should be moved to the destination tile even if not initially placed"
    );
  }

  /**
   * Test getting the action type of the snake action.
   *
   * <p>Expected: The action type retrieved should be alike
   * to the static actionType of the snake action.</p>
   */
  @Test
  public void testGetActionType() {
    assertEquals(SnakeAction.actionType, snakeAction.getActionType());
  }

  /**
   * Test getting the destination tile of the ladder action.
   *
   * <p>Expected: The destination tile retrieved should be the same as
   * the destination tile of the ladder action.</p>
   */
  @Test
  public void testGetDestinationTile() {
    assertTrue(snakeAction.getDestinationTile().isPresent());
    assertEquals(destinationTile, snakeAction.getDestinationTile().get());
  }

  // ------------- Negative tests -------------

  /**
   * Tests if a SnakeAction is created with a null destination tile.
   *
   * <p>Expected: IllegalArgumentException should be thrown. </p>
   */
  @Test
  public void testSnakeActionWithNullDestinationTile() {
    assertThrows(IllegalArgumentException.class,
          () -> new SnakeAction(null),
          "Creating a snake action with a null destination tile should throw an exception"
    );
  }

  /**
   * Tests if a SnakeAction is performed with a null player.
   *
   * <p>Expected: IllegalArgumentException should be thrown. </p>
   */
  @Test
  public void testSnakeActionWithNullPlayer() {
    assertThrows(IllegalArgumentException.class,
          () -> snakeAction.perform(null),
          "Performing a snake action with a null player should throw an exception"
    );
  }

  /**
   * Tests if a SnakeAction is performed when a player is on a lower tile.
   *
   * <p>Expected: IllegalArgumentException should be thrown. </p>
   */
  @Test
  public void testSnakeActionWithPlayerClimbingUp() {
    Tile higherTile = new Tile(destinationTile.getTileId() - 1, 2, 2);
    player.placeOnTile(higherTile);
    assertThrows(IllegalStateException.class,
          () -> snakeAction.perform(player),
          "Player should not be able to climb up a snake"
    );
  }
}

