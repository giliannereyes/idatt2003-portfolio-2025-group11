package edu.ntnu.idi.idatt.domain.action.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for {@link GoToJailAction}.
 * Tests cover jail tile and player initialization and jail action behavior.
 *
 * @version 0.1
 * @author Gilianne Reyes
 */
public class GoToJailActionTest {
  private Player player;
  private Tile regularTile;
  private Tile jailTile;
  private GoToJailAction goToJailAction;

  /**
   * Initializes a new Player, a regular tile, a jail tile,
   * and a GoToJailAction instance before each test. Player is manually placed on the regular tile.
   */
  @BeforeEach
  public void setUp() {
    player = new Player("TestPlayer");
    regularTile = new Tile(5, 1, 0);
    jailTile = new Tile(0, 0, 0);
    goToJailAction = new GoToJailAction(jailTile);
    player.placeOnTile(regularTile);
  }

  // ------------- Positive tests -------------

  /**
   * Tests if the GoToJailAction is executed properly and the player is moved to the jail tile.
   *
   * <p>Expected: Player should have moved to the jail tile and skip-turn should be set to true.</p>
   */
  @Test
  public void testValidGoToJailAction() {
    assertEquals(regularTile, player.getCurrentTile());
    goToJailAction.perform(player);
    assertEquals(jailTile, player.getCurrentTile(), "Player should have moved to the jail tile");
    assertTrue(player.willSkipTurn(), "Player should skip their next turn");
  }

  /**
   * Tests GoToJailAction when player is already on the jail tile.
   *
   * <p>Expected: Player should remain on the jail tile and skip-turn should be set to true. </p>
   */
  @Test
  public void testGoToJailActionWhenPlayerAlreadyInJail() {
    player.placeOnTile(jailTile);
    goToJailAction.perform(player);
    assertEquals(jailTile, player.getCurrentTile(), "Player should stay on the jail tile");
    assertTrue(player.willSkipTurn(), "Player should skip their next turn");
  }

  /**
   * Test getting the action type of the GoToJailAction.
   *
   * <p>Expected: The action type retrieved should be equal to the static actionType string. </p>
   */
  @Test
  public void testGetActionType() {
    assertEquals(GoToJailAction.actionType, goToJailAction.getActionType());
  }

  /**
   * Test getting the destination tile of the GoToJailAction.
   *
   * <p>Expected: The destination tile retrieved should match the jail tile. </p>
   */
  @Test
  public void testGetDestinationTile() {
    assertTrue(goToJailAction.getDestinationTile().isPresent());
    assertEquals(jailTile, goToJailAction.getDestinationTile().get());
  }

  // ------------- Negative tests -------------
  /**
   * Tests if a GoToJailAction is created with a null jail tile.
   *
   * <p>Expected: IllegalArgumentException should be thrown. </p>
   */
  @Test
  public void testGoToJailActionWithNullJailTile() {
    assertThrows(IllegalArgumentException.class,
          () -> new GoToJailAction(null),
          "Creating GoToJailAction with a null jail tile should throw an exception"
    );
  }

  /**
   * Tests if the GoToJailAction is performed with a null player.
   *
   * <p>Expected: IllegalArgumentException should be thrown. </p>
   */
  @Test
  public void testGoToJailActionWithNullPlayer() {
    assertThrows(IllegalArgumentException.class,
          () -> goToJailAction.perform(null),
          "Performing GoToJailAction with a null player should throw an exception"
    );
  }
}
