package edu.ntnu.idi.idatt.model.events.types;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;

/**
 * Represents an event where a player moves from one tile to another.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class PlayerMovedEvent implements GameEvent {
    private final Player player;
    private final Tile fromTile;
    private final Tile destinationTile;

    /**
     * Constructs a PlayerMovedEvent instance.
     *
     * @param player is the player that moved.
     * @param fromTile is the tile player moved from.
     * @param destinationTile is the tile the player moved to.
     */
    public PlayerMovedEvent(Player player, Tile fromTile, Tile destinationTile) {
        this.player = player;
        this.fromTile = fromTile;
        this.destinationTile = destinationTile;
    }

    /**
     * Gets the player that moved.
     *
     * @return the player that moved.
     */
    public Player getPlayer() {
        return player;
    }

    public Tile getDestinationTile() {
        return destinationTile;
    }

    public Tile getFromTile() {
        return fromTile;
    }
}
