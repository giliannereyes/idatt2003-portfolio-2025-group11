package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

/**
 * Represents an event where a player rolls the dice.
 *
 * @author Gilianne Reyes
 * @version 0.1
 * @since 0.1
 */
public record DiceRolledEvent(Player player, int roll1, int roll2) implements GameEvent {}
