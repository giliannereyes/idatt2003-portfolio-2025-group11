package edu.ntnu.idi.idatt.domain.event.common;

/**
 * Listener interface for handling {@link PlayerWonEvent}s.
 *
 * <p>Implementations will be notified when a player wins the game.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface PlayerWonListener {
  /**
   * Called when a player has won.
   *
   * @param e is the {@link PlayerWonEvent} describing the winner.
   */
  void onPlayerWon(PlayerWonEvent e);
}
