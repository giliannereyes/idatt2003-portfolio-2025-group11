package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

/**
 * Represents an event where a player moves from one tile to another.
 *
 * @author Gilianne Reyes
 * @version 0.1
 * @since 0.1
 */
public record PlayerMovedEvent(Player player, Tile fromTile, Tile destinationTile) implements GameEvent {}
