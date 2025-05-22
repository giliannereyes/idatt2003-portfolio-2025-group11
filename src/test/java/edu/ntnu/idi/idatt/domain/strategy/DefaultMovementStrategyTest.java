package edu.ntnu.idi.idatt.domain.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idi.idatt.domain.entity.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link DefaultMovementStrategy} class.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class DefaultMovementStrategyTest {
  Tile startTile;
  Tile nextTile;
  DefaultMovementStrategy strategy;

  /**
   * Sets up a new start tile, next tile, and movement strategy before each test.
   */
  @BeforeEach
  void setUp() {
    startTile = new Tile(1, 0, 0);
    nextTile = new Tile(2, 1, 0);
    startTile.setNextTile(nextTile);
    strategy = new DefaultMovementStrategy();
  }

  // ------- Positive tests -------
  /**
   * Tests if the destination tile is correctly determined with one step.
   *
   * <p>Expected outcome: The destination tile is the next tile.</p>
   */
  @Test
  void testDetermineDestination() {
    Tile destinationTile = strategy.determineDestination(startTile, 1);
    assertEquals(nextTile, destinationTile);
  }

  /**
   * Tests if the destination tile is correctly determined with zero steps.
   *
   * <p>Expected outcome: The destination tile is the start tile.</p>
   */
  @Test
  void testDetermineDestinationZeroSteps() {
    Tile destinationTile = strategy.determineDestination(startTile, 0);
    assertEquals(startTile, destinationTile);
  }

  // ------- Negative tests -------

  /**
   * Tests retrieving the destination tile with a null start tile.
   *
   * <p>Expected outcome: An {@link IllegalArgumentException} is thrown.</p>
   */
  @Test
  void testDetermineDestinationNullStartTile() {
    assertThrows(IllegalArgumentException.class,
          () -> strategy.determineDestination(null, 1)
    );
  }

  /**
   * Tests retrieving the destination tile with negative steps.
   *
   * <p>Expected outcome: An {@link IllegalArgumentException} is thrown.</p>
   */
  @Test
  void testDetermineDestinationNegativeSteps() {
    assertThrows(IllegalArgumentException.class,
          () -> strategy.determineDestination(startTile, -1)
    );
  }
}
