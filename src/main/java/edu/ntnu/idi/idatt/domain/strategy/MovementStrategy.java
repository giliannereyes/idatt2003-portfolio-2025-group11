package edu.ntnu.idi.idatt.domain.strategy;

import edu.ntnu.idi.idatt.domain.entity.Tile;

/**
 * Interface for movement strategies which determines the movement of the player.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface MovementStrategy {
  /**
   * Determines the movement of the player based on the current tile and the target tile.
   *
   * @param currentTile is the tile the player is currently on.
   * @param steps is the amount of steps the player will take.
   *
   * @return the tile the player will move to.
   */
  Tile determineDestination(Tile currentTile, int steps);
}
