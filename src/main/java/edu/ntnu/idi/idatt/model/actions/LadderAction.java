package edu.ntnu.idi.idatt.model.actions;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;

/**
 * LadderAction class is a class that represents the action of a ladder tile.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class LadderAction implements TileAction {
    private final Tile destinationTile;

    /**
     * Constructs a LadderAction instance.
     *
     * @param destinationTile is the tile the player is placed on with this action.
     */
    public LadderAction(Tile destinationTile) {
        this.destinationTile = destinationTile;
    }

    /**
     * Moves the player to the destination tile.
     *
     * @param player is the player that landed on the tile.
     */
    @Override
    public void perform(Player player) {
        player.placeOnTile(destinationTile);
        // Testing purposes
        System.out.println("Player" + player.getName() + " climbed to " + destinationTile);
    }
}
