package edu.ntnu.idi.idatt.model.actions;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;

/**
 * SnakeAction class is a class that represents the action of a tile with a skip-turn effect.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class SkipTurnAction implements TileAction {
    private final Tile destinationTile;

    /**
     * Constructs a SkipTurnAction instance.
     *
     * @param destinationTile is the tile the player is placed on with this action.
     */
    public SkipTurnAction(Tile destinationTile) {
        this.destinationTile = destinationTile;
    }

    /**
     * Moves the player to the destination tile.
     *
     * @param player is the player that landed on the tile.
     */
    public void perform(Player player) {
        player.placeOnTile(destinationTile);
        System.out.println("Player " + player.getName() + " skipped turn");
    }
}