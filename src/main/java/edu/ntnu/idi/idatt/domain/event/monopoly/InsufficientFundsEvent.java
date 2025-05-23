package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

/**
 * Event fired when a player does not have enough funds to buy a property.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 * @param player is the player who attempted the purchase.
 * @param property is the property the player attempted to buy.
 */
public record InsufficientFundsEvent(Player player, Property property) implements GameEvent {}
