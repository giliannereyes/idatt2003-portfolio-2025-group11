package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

/**
 * Represents an event where a player rolls the dice.
 *
 * <p>Encapsulates the {@link Player} who rolled and the two dice values.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 * @param player the player who rolled.
 * @param roll1 is the value of the first die (1–6)
 * @param roll2 is the value of the second die (1–6)
 */
public record DiceRolledEvent(Player player, int roll1, int roll2) implements GameEvent {}
