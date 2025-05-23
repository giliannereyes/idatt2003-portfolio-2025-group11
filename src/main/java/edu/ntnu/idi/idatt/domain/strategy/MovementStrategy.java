package edu.ntnu.idi.idatt.domain.strategy;

import edu.ntnu.idi.idatt.domain.entity.Tile;

/**
 * Strategy abstraction for determining how far and where a player moves.
 *
 * <p>Implementations encapsulate game‐specific rules (e.g., wrap‐around,
 * board boundaries, special shortcuts).</p>
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
   * @return the {@link Tile} the player will move to.
   */
  Tile determineDestination(Tile currentTile, int steps);
}
