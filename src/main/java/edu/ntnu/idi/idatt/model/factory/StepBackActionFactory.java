package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.StepBackAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Tile;

/**
 * A class that creates a step back action based on the destination tile.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class StepBackActionFactory implements TileActionFactory {
    /**
     * Creates a step-back snake action based on the destination tile.
     *
     * @param destinationTile is the tile the player is placed on with this action.
     *
     * @return an instance of the step-back action.
     */
    @Override
    public TileAction createTileAction(Tile destinationTile) {
        return new StepBackAction(destinationTile);
    }
}
