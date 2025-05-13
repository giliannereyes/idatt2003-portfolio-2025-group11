package edu.ntnu.idi.idatt.domain.action.snakesandladders;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * ResetAction is a class that represents the action of moving the player to the start tile
 * when the player lands on a reset tile.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class ResetAction implements TileAction {
    public static final String actionType = "ResetAction";

    /**
     * Moves the player to the start tile.
     *
     * @param player is the player that landed on the tile.
     */
    @Override
    public void perform(Player player) {
        Validation.validateNonNull(player, "Player");
        Tile startTile = player.getStartTile();
        player.placeOnTile(startTile);
    }

    /**
     * Retrieves the type of the action, which is "ResetAction".
     *
     * @return the type of the action.
     */
    @Override
    public String getActionType() {
        return actionType;
    }
}
