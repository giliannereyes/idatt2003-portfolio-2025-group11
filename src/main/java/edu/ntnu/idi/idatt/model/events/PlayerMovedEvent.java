package edu.ntnu.idi.idatt.model.events;

import edu.ntnu.idi.idatt.model.entities.Player;

/**
 * Represents an event where a player moves from one tile to another.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class PlayerMovedEvent implements GameEvent{
    private final Player player;
    private final int fromTileId;
    private final int toTileId;

    /**
     * Constructs a PlayerMovedEvent instance.
     *
     * @param player is the player that moved.
     * @param fromTileId is the id of the tile the player moved from.
     * @param toTileId is the id of the tile the player moved to.
     */
    public PlayerMovedEvent(Player player, int fromTileId, int toTileId) {
        this.player = player;
        this.fromTileId = fromTileId;
        this.toTileId = toTileId;
    }

    /**
     * Gets the player that moved.
     *
     * @return the player that moved.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the id of the tile the player moved from.
     *
     * @return id of the tile the player moved from.
     */
    public int getFromTileId() {
        return fromTileId;
    }

    /**
     * Gets the id of the tile the player moved to.
     *
     * @return id of the tile the player moved to.
     */
    public int getToTileId() {
        return toTileId;
    }
}
