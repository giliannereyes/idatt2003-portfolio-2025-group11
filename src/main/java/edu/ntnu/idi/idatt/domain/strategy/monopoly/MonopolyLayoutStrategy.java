package edu.ntnu.idi.idatt.domain.strategy.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.strategy.BoardLayoutStrategy;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * Layout strategy for a 7×7 “MonopolyLite” board with 24 perimeter tiles.
 * Tiles are numbered 1…24 starting at top-left (“GO”), then clockwise.
 */
public class MonopolyLayoutStrategy implements BoardLayoutStrategy {
  @Override
  public void buildLayout(Board board) {
    Validation.validateNonNull(board, "Board");
    int rows = board.getRows();
    int cols = board.getColumns();
    if (rows < 2 || cols < 2) {
      throw new IllegalArgumentException("Board must be at least 2×2");
    }

    Tile first = null;
    Tile prev  = null;
    int id     = 1;

    // 1) Top row: (0,rows-1) → (cols-1,rows-1)
    for (int x = 0; x < cols; x++, id++) {
      Tile t = new Tile(id, x, rows - 1);
      board.addTile(t);
      if (prev != null) prev.setNextTile(t);
      else first = t;
      prev = t;
    }

    // 2) Right column: (cols-1,rows-2) → (cols-1,0)
    for (int y = rows - 2; y >= 0; y--, id++) {
      Tile t = new Tile(id, cols - 1, y);
      board.addTile(t);
      prev.setNextTile(t);
      prev = t;
    }

    // 3) Bottom row: (cols-2,0) → (0,0)
    for (int x = cols - 2; x >= 0; x--, id++) {
      Tile t = new Tile(id, x, 0);
      board.addTile(t);
      prev.setNextTile(t);
      prev = t;
    }

    // 4) Left column: (0,1) → (0,rows-2)
    for (int y = 1; y <= rows - 2; y++, id++) {
      Tile t = new Tile(id, 0, y);
      board.addTile(t);
      prev.setNextTile(t);
      prev = t;
    }

    // Close the loop back to the first tile
    prev.setNextTile(first);
  }
}
