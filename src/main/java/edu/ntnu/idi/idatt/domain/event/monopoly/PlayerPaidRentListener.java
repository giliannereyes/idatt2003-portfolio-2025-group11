package edu.ntnu.idi.idatt.domain.event.monopoly;

/**
 * Listener interface for handling {@link PlayerPaidRentEvent}s.
 *
 * <p>Implementations will be notified when a rent payment occurs between players.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface PlayerPaidRentListener {
  /**
   * Called when a player pays rent to another player.
   *
   * @param e is the {@link PlayerPaidRentEvent} containing tenant, landlord, rent amount,
   *          and updated balances.
   */
  void onRentPaid(PlayerPaidRentEvent e);
}
