package edu.ntnu.idi.idatt.domain.factory.snakesandladders;

import edu.ntnu.idi.idatt.domain.action.snakesandladders.LadderAction;
import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.factory.DestinationTileActionFactory;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * A class that creates a ladder action based on the destination tile.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class LadderActionFactory implements DestinationTileActionFactory {
    /**
     * Creates a ladder action based on the destination tile.
     *
     * @param destinationTile is the tile the player is placed on with this action.
     *
     * @return an instance of the ladder action.
     *
     * @throws IllegalArgumentException if the destination tile is null.
     */
    @Override
    public TileAction createTileAction(Tile destinationTile) {
        Validation.validateNonNull(destinationTile, "Destination tile");
        return new LadderAction(destinationTile);
    }

    /**
     * Retrieves the type of the action.
     *
     * @return the type of the action.
     */
    @Override
    public String getActionType() {
        return LadderAction.actionType;
    }
}
