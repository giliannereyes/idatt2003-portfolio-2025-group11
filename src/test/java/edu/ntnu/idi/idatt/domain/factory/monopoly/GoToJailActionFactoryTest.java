package edu.ntnu.idi.idatt.domain.factory.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.action.monopoly.GoToJailAction;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for the {@link GoToJailActionFactory} class.
 * This test ensures that the factory correctly creates a {@link GoToJailAction}
 * and that it enforces its null‐argument contract.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class GoToJailActionFactoryTest {
  private Tile jailTile;
  private GoToJailActionFactory factory;

  /**
   * Initializes a jail tile and a {@link GoToJailActionFactory} instance.
   */
  @BeforeEach
  public void setUp() {
    jailTile = new Tile(5, 0, 0);
    factory = new GoToJailActionFactory();
  }

  // ----------- Positive Tests -----------

  /**
   * Tests whether the {@link GoToJailActionFactory} correctly creates a {@link GoToJailAction}.
   *
   * <p>Expected: The factory should return a non-null instance of
   * {@link GoToJailAction} configured with the jail tile.</p>
   */
  @Test
  public void testCreateGoToJailTileAction() {
    TileAction action = factory.createTileAction(jailTile);

    assertNotNull(action, "GoToJailAction should not be null");
    assertInstanceOf(GoToJailAction.class, action,
          "The factory should return an instance of GoToJailAction");
  }

  /**
   * Tests that the factory creates a new instance of {@link GoToJailAction} each time.
   *
   * <p>Expected: The factory should return a distinct object on each invocation.</p>
   */
  @Test
  public void testFactoryCreatesNewInstances() {
    TileAction action1 = factory.createTileAction(jailTile);
    TileAction action2 = factory.createTileAction(jailTile);

    assertNotSame(action1, action2, "Factory should return a new instance each time");
  }

  /**
   * Tests that the factory returns the correct action type.
   *
   * <p>Expected: The factory's action type string matches
   * {@link GoToJailAction#actionType}.</p>
   */
  @Test
  public void testGetActionType() {
    assertEquals(GoToJailAction.actionType, factory.getActionType(),
          "Factory should return GoToJailAction.actionType");
  }

  // ----------- Negative Tests -----------

  /**
   * Tests creating a {@link GoToJailAction} with a null destination tile.
   *
   * <p>Expected: An {@link IllegalArgumentException} should be thrown.</p>
   */
  @Test
  public void testCreateGoToJailTileActionNullDestinationTile() {
    assertThrows(IllegalArgumentException.class,
          () -> factory.createTileAction(null),
          "Passing null jail tile should throw IllegalArgumentException");
  }
}

