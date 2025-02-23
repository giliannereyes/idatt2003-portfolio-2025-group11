package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.SkipTurnAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Tile;

/**
 * A class that creates a skip turn action based on the destination tile.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class SkipTurnActionFactory implements TileActionFactory {
    /**
     * Creates a skip-turn action based on the destination tile.
     *
     * @param destinationTile is the tile the player is placed on with this action.
     *
     * @return an instance of the skip-turn action.
     */
    @Override
    public TileAction createTileAction(Tile destinationTile) {
        return new SkipTurnAction(destinationTile);
    }
}
