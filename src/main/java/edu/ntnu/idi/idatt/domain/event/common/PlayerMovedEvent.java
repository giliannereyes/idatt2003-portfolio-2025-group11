package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

/**
 * Represents an event where a player moves from one tile to another.
 *
 * <p>Encapsulates the {@link Player} who moved, the tile they moved from,
 * and the tile they moved to.</p>
 *
 * @version 0.1
 * @author Gilianne Reyes
 * @param player is the player who moved; never null
 * @param fromTile is the tile the player moved from.
 * @param destinationTile is the tile the player moved to.
 */
public record PlayerMovedEvent(Player player, Tile fromTile, Tile destinationTile)
      implements GameEvent {}
