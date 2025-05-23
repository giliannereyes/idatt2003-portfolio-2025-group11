package edu.ntnu.idi.idatt.domain.strategy.laddersgame;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.strategy.BoardLayoutStrategy;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * Layout strategy for a Snakes & Ladders board. The strategy creates a
 * snake-like layout where the tiles are arranged in rows, alternating
 * the direction of the rows.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class LaddersGameLayoutStrategy implements BoardLayoutStrategy {
  /**
   * Builds the layout of the board by adding tiles in a snake-like pattern.
   *
   * @param board the board to build the layout for; must not be null
   * @throws IllegalArgumentException if {@code board} is null.
   * @throws IllegalArgumentException if {@code board} has fewer than 1 row or 1 column.
   */
  @Override
  public void buildLayout(Board board) {
    Validation.validateNonNull(board, "Board");
    int rows = board.getRows();
    int columns = board.getColumns();
    if (rows < 1 || columns < 1) {
      throw new IllegalArgumentException("Board must have at least 1 row and 1 column");
    }
    int tileCount = rows * columns;
    Tile previousTile = null;
    for (int i = 1; i <= tileCount; i++) {
      Tile tile = getTile(i, columns);
      board.addTile(tile);
      if (i == 1) {
        board.setStartTile(tile);
      }

      if (previousTile != null) {
        previousTile.setNextTile(tile);
      }
      previousTile = tile;
    }
  }

  /**
   * Calculates the row and column for a given tile ID and returns the corresponding Tile.
   *
   * @param i is the sequential tile ID (1-based)
   * @param columns is the number of columns in the board.
   *
   * @return a new {@link Tile} with the calculated coordinates.
   */
  private Tile getTile(int i, int columns) {
    int row = (i - 1) / columns;
    int col = (i - 1) % columns;
    if (row % 2 == 1) {
      col = columns - 1 - col;
    }
    return new Tile(i, col, row);
  }
}

