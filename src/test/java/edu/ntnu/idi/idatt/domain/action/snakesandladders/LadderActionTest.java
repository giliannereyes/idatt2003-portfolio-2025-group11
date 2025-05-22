package edu.ntnu.idi.idatt.domain.action.snakesandladders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for {@link LadderAction}.
 * Tests cover tile and player initialisation and ladder action.
 *
 * @author Trang Duong
 * @version 0.3
 * @since 0.1
 */
public class LadderActionTest {
  private Tile startTile;
  private Tile destinationTile;
  private Player player;
  private LadderAction ladderAction;

  /**
   * Initialises a new Player, a start and destination Tile,
   * and a LandAction instance before each test. Player is manually placed on the start tile.
   */
  @BeforeEach
  public void setUp() {
    startTile = new Tile(1, 0, 0);
    destinationTile = new Tile(10, 4, 1);
    player = new Player("TestPlayer");
    ladderAction = new LadderAction(destinationTile);

    player.placeOnTile(startTile);
  }

  // ------ Positive tests ------

  /**
   * Tests if ladder action is executed properly and player lands on correct destination tile.
   *
   * <p>Expected: Player should be moved to the destination tile of the ladder.</p>
   */
  @Test
  public void testLadderAction() {
    assertEquals(startTile, player.getCurrentTile());
    ladderAction.perform(player);
    assertEquals(destinationTile, player.getCurrentTile(),
          "Player should have moved to the destination tile"
    );
  }

  /**
   * Tests if Player is already on destination tile.
   *
   * <p>Expected: Player should stay on the destination tile.</p>
   */
  @Test
  public void testLadderActionWhenPlayerAlreadyOnDestination() {
    player.placeOnTile(destinationTile);
    ladderAction.perform(player);
    assertEquals(destinationTile, player.getCurrentTile(),
          "Player should stay on the destination tile"
    );
  }

  /**
   * Tests LadderAction when player is not placed on any tile.
   *
   * <p>Expected: Player should be moved to the destination tile even if not initially placed.</p>
   */
  @Test
  public void testLadderActionWhenPlayerNotPlacedOnAnyTile() {
    Player newPlayer = new Player("UnplacedPlayer");
    ladderAction.perform(newPlayer);
    assertEquals(destinationTile, newPlayer.getCurrentTile(),
          "Player should be moved to the destination tile even if not initially placed"
    );
  }

  /**
   * Test getting the action type of the ladder action.
   *
   * <p>Expected: The action type retrieved should be alike
   * to the static actionType of the ladder action.</p>
   */
  @Test
  public void testGetActionType() {
    assertEquals(LadderAction.actionType, ladderAction.getActionType());
  }

  /**
   * Test getting the destination tile of the ladder action.
   *
   * <p>Expected: The destination tile retrieved should be the same as
   * the destination tile of the ladder action.</p>
   */
  @Test
  public void testGetDestinationTile() {
    assertTrue(ladderAction.getDestinationTile().isPresent());
    assertEquals(destinationTile, ladderAction.getDestinationTile().get());
  }

  // ------ Negative tests ------
  /**
   * Tests performing a ladder action with a null destination tile.
   *
   * <p>Expected: IllegalArgumentException should be thrown.</p>
   */
  @Test
  public void testLadderActionWithNullDestinationTile() {
    assertThrows(IllegalArgumentException.class, () -> new LadderAction(null));
  }

  /**
   * Tests performing a ladder action with a null player.
   *
   * <p>Expected: IllegalArgumentException should be thrown.</p>
   */
  @Test
  public void testLadderActionWithNullPlayer() {
    assertThrows(IllegalArgumentException.class, () -> ladderAction.perform(null));
  }

  /**
   * Tests performing a ladder action with a player trying to climb down.
   *
   * <p>Expected: IllegalStateException should be thrown.</p>
   */
  @Test
  public void testLadderActionWithPlayerClimbingDown() {
    Tile higherTile = new Tile(20, 2, 1);
    player.placeOnTile(higherTile);
    assertThrows(IllegalStateException.class, () -> ladderAction.perform(player));
  }
}
