package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

/**
 * Event fired when a player goes bankrupt.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 * @param player is the player who has gone bankrupt.
 */
public record PlayerBankruptEvent(Player player) implements GameEvent {
}
