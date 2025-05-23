package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

/**
 * Event fired when a player wins the game.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 * @param winner is the player who won.
 */
public record PlayerWonEvent(Player winner) implements GameEvent {
}
