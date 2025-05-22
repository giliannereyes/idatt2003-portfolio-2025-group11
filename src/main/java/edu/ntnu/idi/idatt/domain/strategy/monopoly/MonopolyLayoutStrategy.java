package edu.ntnu.idi.idatt.domain.strategy.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.strategy.BoardLayoutStrategy;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * Layout strategy for a perimeter-based Monopoly board.
 *
 * <p>Tiles are numbered 1…N clockwise from the "GO" corner at top-left,
 * covering the top row, right column, bottom row, and left column in sequence.
 * </p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class MonopolyLayoutStrategy implements BoardLayoutStrategy {
  /**
   * Builds the layout by placing tiles around the perimeter of the board.
   *
   * @param board is the board to populate.
   *
   * @throws IllegalArgumentException if {@code board} has fewer than 2 rows or columns.
   * @throws IllegalArgumentException if {@code board} is null.
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

    // Build top row
    prev = first = buildTiles(board, id, 0, rows - 1, 1, 0, cols, null);
    // Build right column
    id += cols;
    prev = buildTiles(board, id, cols - 1, rows - 2, 0, -1, rows - 1, prev);
    // Build bottom row
    id += rows - 1;
    prev = buildTiles(board, id, cols - 2, 0, -1, 0, cols - 1, prev);
    // Build left column
    id += cols - 1;
    prev = buildTiles(board, id, 0, 1, 0, 1, rows - 2, prev);
    // Close the loop
    prev.setNextTile(first);
  }

  /**
   * Helper method to build a sequence of tiles in a straight line.
   *
   * @param board the board to populate; must not be null
   * @param startId the starting ID for the tiles
   * @param startX the starting X-coordinate
   * @param startY the starting Y-coordinate
   * @param stepX the step size for X-coordinate
   * @param stepY the step size for Y-coordinate
   * @param count the number of tiles to create
   * @param prev the previously created tile to link to; can be null for the first tile
   *
   * @return the last tile created in the sequence
   */
  private Tile buildTiles(
        Board board, int startId, int startX, int startY, int stepX, int stepY, int count, Tile prev
  ) {
    for (int i = 0; i < count; i++) {
      Tile t = new Tile(startId + i, startX + i * stepX, startY + i * stepY);
      board.addTile(t);
      if (prev == null) {
        board.setStartTile(t); // Set the first tile as the start tile
      } else {
        prev.setNextTile(t);
      }
      prev = t;
    }
    return prev;
  }
}