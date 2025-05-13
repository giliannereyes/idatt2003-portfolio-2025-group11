package edu.ntnu.idi.idatt.domain.factory.snakesandladders;

import edu.ntnu.idi.idatt.domain.action.snakesandladders.SkipTurnAction;
import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.factory.NoDestinationTileActionFactory;

/**
 * A class that creates a skip turn action based on the destination tile.
 *
 * @version 0.2
 * @since 0.1
 * @author Trang Duong
 */
public class SkipTurnActionFactory implements NoDestinationTileActionFactory {
    /**
     * Creates a skip-turn action based on the destination tile.
     *
     * @return an instance of the skip-turn action.
     */
    @Override
    public TileAction createTileAction() {
        return new SkipTurnAction();
    }

    /**
     * Retrieves the type of the action.
     *
     * @return the type of the action.
     */
    @Override
    public String getActionType() {
        return SkipTurnAction.actionType;
    }
}
