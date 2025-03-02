package edu.ntnu.idi.idatt.model.strategies;

import edu.ntnu.idi.idatt.model.entities.Tile;
import edu.ntnu.idi.idatt.utils.Validation;

import java.util.Optional;

/**
 * A class that determines the target tile based on default movement.
 * Default movement is moving a certain number of steps forward.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class DefaultMovementStrategy implements MovementStrategy {
  /**
   * Determines the destination tile based on the default movement strategy.
   *
   * @param startTile is the tile the player is currently on.
   * @param steps is the amount of steps the player will take.
   *
   * @return the destination tile.
   *
   * @throws IllegalArgumentException if the start tile is null or if the steps are negative.
   * @throws IllegalStateException if the destination tile is not present.
   */
  @Override
  public Tile determineDestination(Tile startTile, int steps) {
    Validation.validateNonNull(startTile, "Start tile");
    Validation.validateNonNegativeNum(steps, "Steps");
    if (steps == 0) {
      return startTile;
    }
    Tile currentTile = startTile;
    for (int i = 0; i < steps; i++) {
      // Stop at the last tile if no further tiles are available
      Optional<Tile> nextTile = currentTile.getNextTile();
      if (nextTile.isEmpty()) {
        return currentTile;  // Return the last available tile
      }
      currentTile = nextTile.get();
    }
    return currentTile;
  }
}
