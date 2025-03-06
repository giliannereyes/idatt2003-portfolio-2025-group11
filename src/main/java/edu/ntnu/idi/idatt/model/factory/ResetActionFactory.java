package edu.ntnu.idi.idatt.model.factory;

import edu.ntnu.idi.idatt.model.actions.ResetAction;
import edu.ntnu.idi.idatt.model.actions.TileAction;

/**
 * A class that creates a restart action.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class ResetActionFactory implements NoDestinationTileActionFactory {
    /**
     * Creates a return-to-start action.
     *
     * @return an instance of the return-to-start action.
     */
    @Override
    public TileAction createTileAction() {
        return new ResetAction();
    }

    /**
     * Retrieves the type of the action.
     *
     * @return the type of the action.
     */
    @Override
    public String getActionType() {
        return ResetAction.actionType;
    }
}
