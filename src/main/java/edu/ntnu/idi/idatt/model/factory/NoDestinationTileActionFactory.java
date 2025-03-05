package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.TileAction;

/**
 * Interface for creating tile actions without a destination tile.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface NoDestinationTileActionFactory {
    /**
     * Creates a tile action without a destination tile.
     *
     * @return an instance of the tile action.
     */
    TileAction createTileAction();

    /**
     * Retrieves the type of the action.
     *
     * @return the type of the action.
     */
    String getActionType();
}
