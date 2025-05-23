package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.monopoly.AssetsAccount;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

/**
 * Event fired when a player requests to buy a property in Monopoly.
 *
 * <p>Encapsulates the {@link Player} attempting the purchase, the
 * {@link Property} they wish to buy, and their current {@link AssetsAccount}.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 * @param player is the player making the purchase request.
 * @param property is the property being requested for purchase.
 * @param account is the player's assets account at the time of request.
 */
public record BuyPropertyRequestEvent(Player player, Property property, AssetsAccount account)
      implements GameEvent {}

