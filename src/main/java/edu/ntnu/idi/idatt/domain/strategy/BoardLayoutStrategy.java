package edu.ntnu.idi.idatt.domain.strategy;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Tile;

/**
 * Strategy interface for laying out {@link Tile}s on a {@link Board}.
 *
 * <p>Implementations arrange tiles (and set the start tile) according to
 * their specific game rules.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface BoardLayoutStrategy {
  /**
   * Populates the given board with tiles in a particular pattern.
   *
   * @param board is the board to build the layout for.
   */
  void buildLayout(Board board);
}
