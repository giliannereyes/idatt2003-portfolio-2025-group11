package edu.ntnu.idi.idatt.domain.event.monopoly;

/**
 * Listener interface for handling {@link PlayerBankruptEvent}s.
 *
 * <p>Implementations will be notified when a player is declared bankrupt.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface PlayerBankruptListener {
  /**
   * Called when a player goes bankrupt.
   *
   * @param e is the {@link PlayerBankruptEvent} containing the bankrupt player.
   */
  void onPlayerBankrupt(PlayerBankruptEvent e);
}
