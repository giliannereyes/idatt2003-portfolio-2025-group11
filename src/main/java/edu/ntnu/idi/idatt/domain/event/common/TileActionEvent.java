package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

/**
 * Event fired when a player lands on a tile and a tile‐specific action should occur.
 *
 * <p>Encapsulates the {@link Player} who landed and the {@link Tile} on which they landed.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 * @param player is the player who landed.
 * @param tile is the tile landed upon.
 */
public record TileActionEvent(Player player, Tile tile) implements GameEvent {}
