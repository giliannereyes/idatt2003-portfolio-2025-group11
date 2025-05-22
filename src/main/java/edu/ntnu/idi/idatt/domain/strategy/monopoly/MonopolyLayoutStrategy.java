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

  @Override
  public void buildLayout(Board board) {
    Validation.validateNonNull(board, "Board");
    int rows = board.getRows();
    int cols = board.getColumns();
    if (rows < 2 || cols < 2) {
      throw new IllegalArgumentException("Board must be at least 2×2");
    }

    Tile first;        // tile ID 1  (GO square)
    Tile prev = null;  // last tile inserted in the current segment
    int  id   = 1;     // running tile-ID

    /* ─── Top row  (left → right) ───────────────────────────── */
    prev = buildTiles(board, id,
          0, rows - 1,          // start (x,y)
          1, 0,                 // step  (x,y)
          cols,                 // tile count
          prev);
    first = board.getTile(1);               // remember tile 1
    board.setStartTile(first);
    id += cols;

    /* ─── Right column (top → bottom, excluding corner already made) */
    prev = buildTiles(board, id,
          cols - 1, rows - 2,
          0, -1,
          rows - 1,
          prev);
    id += rows - 1;

    /* ─── Bottom row (right → left) ─────────────────────────── */
    prev = buildTiles(board, id,
          cols - 2, 0,
          -1, 0,
          cols - 1,
          prev);
    id += cols - 1;

    /* ─── Left column (bottom → top, excluding both corners) ── */
    prev = buildTiles(board, id,
          0, 1,
          0, 1,
          rows - 2,
          prev);

    /* ─── Close the loop ─────────────────────────────────────── */
    prev.setNextTile(first);
  }

  /**
   * Creates {@code count} tiles in a straight line and links them to
   * {@code prev}. Returns the last tile created so that the caller can
   * continue chaining in the next perimeter segment.
   */
  private Tile buildTiles(Board board,
                          int startId,
                          int startX, int startY,
                          int stepX,  int stepY,
                          int count,
                          Tile prev) {

    for (int i = 0; i < count; i++) {
      int id = startId + i;
      int x  = startX + i * stepX;
      int y  = startY + i * stepY;

      Tile t = new Tile(id, x, y);
      board.addTile(t);

      if (prev != null) {
        prev.setNextTile(t);
      }
      prev = t;
    }
    return prev;   // last tile in this segment
  }
}
