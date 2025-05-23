package edu.ntnu.idi.idatt.domain.strategy.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.strategy.BoardLayoutStrategy;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * Places tiles around the perimeter of a rectangular board and links them
 * clockwise in a continuous ring (1 → 2 → … → N → 1).
 *
 * @author Gilianne Reyes
 * @version 0.2
 */
public class MonopolyLayoutStrategy implements BoardLayoutStrategy {

  /**
   * Builds the layout of the board by adding tiles in a clockwise pattern.
   *
   * @param board is the board to build the layout for.
   *
   * @throws IllegalArgumentException if {@code board} is null.
   * @throws IllegalArgumentException if {@code board} has fewer than 2 rows or 2 columns.
   */
  @Override
  public void buildLayout(Board board) {
    Validation.validateNonNull(board, "Board");
    int rows = board.getRows();
    int cols = board.getColumns();
    if (rows < 2 || cols < 2) {
      throw new IllegalArgumentException("Board must be at least 2×2");
    }
    Tile first;
    Tile prev;
    int id = 1;
    prev = buildTiles(board, id, 0, rows - 1, 1, 0, cols, null);
    first = board.getTile(1);
    board.setStartTile(first);
    id += cols;
    prev = buildTiles(board, id, cols - 1, rows - 2, 0, -1, rows - 1, prev);
    id += rows - 1;
    prev = buildTiles(board, id, cols - 2, 0, -1, 0, cols - 1, prev);
    id += cols - 1;
    prev = buildTiles(board, id, 0, 1, 0, 1, rows - 2, prev);
    prev.setNextTile(first);
  }

  /**
   * Builds a series of tiles in a straight line on the board.
   *
   * @param board the board to build the tiles on.
   * @param startId the starting ID for the tiles.
   * @param startX the starting X coordinate.
   * @param startY the starting Y coordinate.
   * @param stepX the step size in the X direction.
   * @param stepY the step size in the Y direction.
   * @param count the number of tiles to build.
   * @param prev the previous tile to link to.
   *
   * @return the last tile created.
   */
  private Tile buildTiles(
        Board board, int startId, int startX, int startY,
        int stepX, int stepY, int count, Tile prev
  ) {
    for (int i = 0; i < count; i++) {
      int id = startId + i;
      int x = startX + i * stepX;
      int y = startY + i * stepY;

      Tile t = new Tile(id, x, y);
      board.addTile(t);

      if (prev != null) {
        prev.setNextTile(t);
      }
      prev = t;
    }
    return prev;
  }
}