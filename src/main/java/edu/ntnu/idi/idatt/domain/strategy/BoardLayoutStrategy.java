package edu.ntnu.idi.idatt.domain.strategy;

import edu.ntnu.idi.idatt.domain.entity.Board;

/**
 * Interface for board layout strategies.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface BoardLayoutStrategy {
    /**
     * Builds the layout of the board.
     *
     * @param board is the board to build the layout for.
     */
    void buildLayout(Board board);
}
