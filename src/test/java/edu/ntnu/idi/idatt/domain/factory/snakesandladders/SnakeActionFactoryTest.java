package edu.ntnu.idi.idatt.domain.factory.snakesandladders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.action.snakesandladders.SnakeAction;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for the {@link SnakeActionFactory} class.
 * This test ensures that the factory correctly creates a {@link SnakeAction}
 * and that it performs the expected behavior when executed.
 *
 * @author Trang Duong
 * @version 0.2
 * @since 0.1
 */
public class SnakeActionFactoryTest {
  private Tile destinationTile;
  private SnakeActionFactory snakeActionFactory;

  /**
   * Initialises a destination tile and a {@link SnakeActionFactory} instance.
   */
  @BeforeEach
  public void setUp() {
    destinationTile = new Tile(10, 4, 1);
    snakeActionFactory = new SnakeActionFactory();
  }

  // -------------- Positive tests --------------

  /**
   * Tests the creation of a {@link SnakeAction} instance.
   *
   * <p>Expected: The factory should return a new instance of
   * {@link SnakeAction} with the destination tile.</p>
   */
  @Test
  public void testCreateSnakeTileAction() {
    TileAction action = snakeActionFactory.createTileAction(destinationTile);
    assertNotNull(action, "Snake action should not be null");
    assertInstanceOf(SnakeAction.class, action,
          "The factory should return an instance of SnakeAction"
    );
  }

  /**
   * Tests that the factory creates independent instances of {@link SnakeAction}.
   *
   * <p>Expected: The factory should return a new instance of
   * {@link SnakeAction} each time it is called.</p>
   */
  @Test
  public void testSnakeActionFactoryCreatesNewInstances() {
    TileAction action1 = snakeActionFactory.createTileAction(destinationTile);
    TileAction action2 = snakeActionFactory.createTileAction(destinationTile);
    assertNotSame(action1, action2, "Factory should return a new instance each time.");
  }

  /**
   * Tests that the factory returns the correct action type.
   *
   * <p>Expected: The factory should return the action type
   * of the {@link SnakeAction}.</p>
   */
  @Test
  public void testGetActionType() {
    assertEquals(SnakeAction.actionType, snakeActionFactory.getActionType());
  }

  // -------------- Negative tests --------------

  /**
   * Tests creating a {@link SnakeAction} with a null destination tile.
   *
   * <p>Expected: An {@link IllegalArgumentException} should be thrown.</p>
   */
  @Test
  public void testCreateTileActionWithNullDestinationTile() {
    assertThrows(IllegalArgumentException.class,
          () -> snakeActionFactory.createTileAction(null)
    );
  }
}