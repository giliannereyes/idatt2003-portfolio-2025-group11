package edu.ntnu.idi.idatt.domain.strategy;

import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.Optional;

/**
 * Default movement strategy: advance forward a fixed number of steps,
 * stopping early if the end of the tile chain is reached.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class DefaultMovementStrategy implements MovementStrategy {
  /**
   * Determines the tile reached by moving {@code steps} tiles forward
   * from {@code startTile}. If a tile has no successor, movement stops there.
   *
   * @param startTile is the tile the player is currently on.
   * @param steps     is the number of steps to move forward.
   *
   * @return the destination {@link Tile}.
   *
   * @throws IllegalArgumentException if {@code startTile} is null or {@code steps} is negative.
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
