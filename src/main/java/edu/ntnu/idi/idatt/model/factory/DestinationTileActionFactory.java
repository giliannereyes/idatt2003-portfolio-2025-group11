package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Tile;

/**
 * Interface for creating tile actions with a destination tile.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface DestinationTileActionFactory {
    /**
     * Creates a tile action based on the destination tile.
     *
     * @param destinationTile is the tile the player is placed on with this action.
     *
     * @return an instance of the tile action.
     */
    TileAction createTileAction(Tile destinationTile);

    /**
     * Retrieves the type of the action.
     *
     * @return the type of the action.
     */
    String getActionType();
}
