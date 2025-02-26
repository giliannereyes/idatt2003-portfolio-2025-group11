package edu.ntnu.idi.idatt.model.actions;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
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
        // Testing purposes
        System.out.println("Player " + player.getName() + " returned to start");
    }
}
