package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.SnakeAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Tile;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * A class that creates a snake action based on the destination tile.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class SnakeActionFactory implements DestinationTileActionFactory {
    /**
     * Creates a snake action based on the destination tile.
     *
     * @param destinationTile is the tile the player is placed on with this action.
     *
     * @return an instance of the snake action.
     *
     * @throws IllegalArgumentException if the destination tile is null.
     */
    @Override
    public TileAction createTileAction(Tile destinationTile) {
        Validation.validateNonNull(destinationTile, "Destination tile");
        return new SnakeAction(destinationTile);
    }
}
