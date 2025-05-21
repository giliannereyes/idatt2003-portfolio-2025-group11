package edu.ntnu.idi.idatt.domain.event.monopoly;

/**
 * Listener interface for handling {@link InsufficientFundsEvent}s.
 *
 * <p>Implementations will be notified when a player attempts lacks sufficient funds
 * to buy a property.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface InsufficientFundsListener {
  /**
   * Called when a player does not have enough funds to buy a property.
   *
   * @param e is the {@link InsufficientFundsEvent} containing the player and property details.
   */
  void onInsufficientFunds(InsufficientFundsEvent e);
}
