package edu.ntnu.idi.idatt.domain.factory.snakesandladders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.action.snakesandladders.LadderAction;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for the {@link LadderActionFactory} class.
 * This test ensures that the factory correctly creates a {@link LadderAction}
 * and that it performs the expected behavior when executed.
 *
 * @author Trang Duong
 * @version 0.2
 * @since 0.1
 */
public class LadderActionFactoryTest {
  private Tile destinationTile;
  private LadderActionFactory ladderActionFactory;

  /**
   * Initialises a destination tile and a {@link LadderActionFactory} instance.
   */
  @BeforeEach
  public void setUp() {
    destinationTile = new Tile(10, 4, 1);
    ladderActionFactory = new LadderActionFactory();
  }

  // ----------- Positive Tests -----------

  /**
   * Tests whether the {@link LadderActionFactory} correctly creates a {@link LadderAction}.
   *
   * <p>Expected: The factory should return a new instance of
   * {@link LadderAction} with the destination tile.</p>
   */
  @Test
  public void testCreateLadderTileAction() {
    TileAction action = ladderActionFactory.createTileAction(destinationTile);

    assertNotNull(action, "Ladder action should not be null");
    assertInstanceOf(LadderAction.class, action, "The factory should return an instance of LadderAction");
  }

  /**
   * Tests that the factory creates a new instance of {@link LadderAction} each time.
   *
   * <p>Expected: The factory should return a new instance each time.</p>
   */
  @Test
  public void testLadderActionFactoryCreatesNewInstances() {
    TileAction action1 = ladderActionFactory.createTileAction(destinationTile);
    TileAction action2 = ladderActionFactory.createTileAction(destinationTile);

    assertNotSame(action1, action2, "Factory should return a new instance each time.");
  }

  /**
   * Tests that the factory returns the correct action type.
   *
   * <p>Expected: The factory should return the action type
   * of the {@link LadderAction}.</p>
   */
  @Test
  public void testGetActionType() {
    assertEquals(LadderAction.actionType, ladderActionFactory.getActionType());
  }

  // ----------- Negative Tests -----------

  /**
   * Tests creating a {@link LadderAction} with a null destination tile.
   *
   * <p>Expected: An {@link IllegalArgumentException} should be thrown.</p>
   */
  @Test
  public void testCreateLadderTileActionNullDestinationTile() {
    assertThrows(IllegalArgumentException.class,
          () -> ladderActionFactory.createTileAction(null)
    );
  }
}