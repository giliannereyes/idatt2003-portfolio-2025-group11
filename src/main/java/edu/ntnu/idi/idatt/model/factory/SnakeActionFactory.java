package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.SnakeAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Tile;

/**
 * A class that creates a snake action based on the destination tile.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class SnakeActionFactory implements TileActionFactory {
    /**
     * Creates a snake action based on the destination tile.
     *
     * @param destinationTile is the tile the player is placed on with this action.
     *
     * @return an instance of the snake action.
     */
    @Override
    public TileAction createTileAction(Tile destinationTile) {
        return new SnakeAction(destinationTile);
    }
}
