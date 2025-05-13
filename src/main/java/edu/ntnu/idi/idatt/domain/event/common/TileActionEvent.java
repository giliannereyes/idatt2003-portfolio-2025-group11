package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

/**
 * Represents an event where a player lands on a tile and an action is triggered.
 *
 * @author Gilianne Reyes
 * @version 0.1
 * @since 0.1
 */
public record TileActionEvent(Player player, Tile tile) implements GameEvent {}
