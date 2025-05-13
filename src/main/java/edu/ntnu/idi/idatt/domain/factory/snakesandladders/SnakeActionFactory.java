package edu.ntnu.idi.idatt.domain.factory.snakesandladders;

import edu.ntnu.idi.idatt.domain.action.snakesandladders.SnakeAction;
import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.factory.DestinationTileActionFactory;
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

    /**
     * Retrieves the type of the action.
     *
     * @return the type of the action.
     */
    @Override
    public String getActionType() {
        return SnakeAction.actionType;
    }
}
