package edu.ntnu.idi.idatt.domain.event.monopoly;

/**
 * Listener interface for handling {@link PlayerPassedGoEvent}s.
 *
 * <p>Implementations will be notified when a player lands on the "Go" tile.</p>
 *
 * @version 0.1
 * @author Gilianne Reyes
 */
public interface PlayerPassedGoListener {
  /**
   * Called when a player lands on the "Go" tile and receives a bonus.
   *
   * @param e is the {@link PlayerPassedGoEvent} containing the player and their new balance.
   */
  void onPlayerPassedGo(PlayerPassedGoEvent e);
}
