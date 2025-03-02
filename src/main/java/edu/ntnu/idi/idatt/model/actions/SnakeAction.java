package edu.ntnu.idi.idatt.model.actions;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * SnakeAction class is a class that represents the action of a snake tile.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class SnakeAction implements TileAction {
    private final Tile destinationTile;

    /**
     * Constructs a SnakeAction instance.
     *
     * @param destinationTile is the tile the player is placed on with this action.
     *
     * @throws IllegalArgumentException if the destination tile is null.
     */
    public SnakeAction(Tile destinationTile) {
        Validation.validateNonNull(destinationTile, "Destination tile");
        this.destinationTile = destinationTile;
    }

    /**
     * Moves the player to the destination tile.
     *
     * @param player is the player that landed on the tile.
     *
     * @throws IllegalArgumentException if the player is null or the player is attempting to climb up.
     */
    @Override
    public void perform(Player player) {
        Validation.validateNonNull(player, "Player");
        if (player.getCurrentTile() != null
                && player.getCurrentTile().getTileId() < destinationTile.getTileId()
        ) {
            throw new IllegalStateException("Player should not be able to climb up a snake.");
        }
        player.placeOnTile(destinationTile);
    }
}