package edu.ntnu.idi.idatt.domain.strategy.snakesandladders;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.strategy.BoardLayoutStrategy;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * Builds the layout of the board for a snakes and ladders game.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class SnakeLayoutStrategy implements BoardLayoutStrategy {
    /**
     * Builds the layout of the board.
     *
     * @param board is the board to build the layout for.
     */
    @Override
    public void buildLayout(Board board) {
        Validation.validateNonNull(board, "Board");
        int rows = board.getRows();
        int columns = board.getColumns();
        int tileCount = rows * columns;
        Tile previousTile = null;
        for (int i = 1; i <= tileCount; i++) {
            Tile tile = getTile(i, columns);
            board.addTile(tile);

            if (previousTile != null) {
                previousTile.setNextTile(tile);
            }
            previousTile = tile;
        }
    }

    /**
     * Retrieves a tile based on the given ID, and the number
     * of columns and rows in the board.
     *
     * @param i is the ID of the tile.
     * @param columns is the number of columns in the board.
     *
     * @return the tile.
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

