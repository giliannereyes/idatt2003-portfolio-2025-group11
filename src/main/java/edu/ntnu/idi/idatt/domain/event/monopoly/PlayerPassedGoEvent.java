package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

/**
 * Event fired when a player lands on the "Go" tile in Monopoly.
 *
 * @param player is the player who landed on the "Go" tile.
 * @param balance is the player's balance after receiving the bonus.
 */
public record PlayerPassedGoEvent(Player player, double balance, double bonus) implements GameEvent {
}

