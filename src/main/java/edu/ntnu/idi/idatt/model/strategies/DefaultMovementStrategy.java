package edu.ntnu.idi.idatt.model.strategies;

import edu.ntnu.idi.idatt.model.entities.Tile;

/**
 * A class that determines the target tile based on default movement.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class DefaultMovementStrategy implements MovementStrategy {
  @Override
  public Tile determineDestination(Tile startTile, int steps) {
    Tile currentTile = startTile;
    for (int i = 0; i < steps; i++) {
      if (!currentTile.isLastTile()) {
        currentTile = currentTile.getNextTile();
      }
    }
    return currentTile;
  }
}
