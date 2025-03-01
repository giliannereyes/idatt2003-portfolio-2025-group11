package edu.ntnu.idi.idatt.model.events.types;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;

/**
 * Represents an event where a player lands on a tile and an action is triggered.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class TileActionEvent implements GameEvent {
    private final Player player;
    private final Tile tile;

    /**
     * Constructs a TileActionEvent instance.
     *
     * @param player is the player that landed on the tile.
     * @param tile is the tile the player landed on.
     */
    public TileActionEvent(Player player, Tile tile) {
        this.player = player;
        this.tile = tile;
    }

    /**
     * Gets the player that landed on the tile.
     *
     * @return the player that landed on the tile.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the tile the player landed on and triggered an action.
     *
     * @return the tile the player landed on and triggered an action.
     */
    public Tile getTile() {
        return tile;
    }
}
