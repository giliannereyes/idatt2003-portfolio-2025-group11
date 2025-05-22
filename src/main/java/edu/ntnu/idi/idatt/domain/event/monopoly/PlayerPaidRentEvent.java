package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.GameEvent;


/**
 * Event fired when a player pays rent to another player.
 *
 * <p>Encapsulates the tenant and landlord involved, the rent amount paid,
 * and each player’s resulting balance.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 * @param tenant is the player who paid the rent
 * @param landlord is the player who received the rent
 * @param rent is the amount of rent paid
 * @param tenantBalance is the tenant’s balance after paying
 * @param landlordBalance is the landlord’s balance after receiving
 */
public record PlayerPaidRentEvent(
      Player tenant, Player landlord, double rent,
      double tenantBalance, double landlordBalance
) implements GameEvent {}
