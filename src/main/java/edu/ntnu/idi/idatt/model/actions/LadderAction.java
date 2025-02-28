package edu.ntnu.idi.idatt.model.actions;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * LadderAction class is a class that represents the action of a ladder tile.
 * A ladder tile moves the player to a higher tile.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class LadderAction implements TileAction {
    private final Tile destinationTile;

    /**
     * Constructs a LadderAction instance.
     *
     * @param destinationTile is the tile the player is placed on with this action.
     *
     * @throws IllegalArgumentException if the destination tile is null.
     */
    public LadderAction(Tile destinationTile) {
        Validation.validateNonNull(destinationTile, "Destination tile");
        this.destinationTile = destinationTile;
    }

    /**
     * Moves the player to the destination tile.
     *
     * @param player is the player that landed on the tile.
     *
     * @throws IllegalArgumentException if the player is null or the player is attempting to climb down.
     */
    @Override
    public void perform(Player player) {
        Validation.validateNonNull(player, "Player");
        if (player.getCurrentTile() != null
                && player.getCurrentTile().getTileId() > destinationTile.getTileId()
        ) {
            throw new IllegalStateException("Player should not be able to climb down a ladder.");
        }
        player.placeOnTile(destinationTile);
        // Testing purposes
        System.out.println("Player" + player.getName() + " climbed to " + destinationTile);
    }
}
