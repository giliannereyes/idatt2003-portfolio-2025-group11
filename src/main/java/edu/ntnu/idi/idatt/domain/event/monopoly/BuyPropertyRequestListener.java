package edu.ntnu.idi.idatt.domain.event.monopoly;

/**
 * Listener interface for handling {@link BuyPropertyRequestEvent}s.
 *
 * <p>Implementations will be notified when a player requests to buy a property.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface BuyPropertyRequestListener {
  /**
   * Called when a player requests to buy a property.
   *
   * @param e is the {@link BuyPropertyRequestEvent} containing the player,
   *          the property requested, and the player's account
   */
  void onBuyPropertyRequest(BuyPropertyRequestEvent e);
}
