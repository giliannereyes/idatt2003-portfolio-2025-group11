package edu.ntnu.idi.idatt.domain.event.common;

/**
 * Listener interface for handling {@link PlayerMovedEvent}s.
 *
 * <p>Implementations of this interface will be notified whenever
 * a player moves on the game board.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface PlayerMovedListener {
  /**
   * Called when a player has moved from one tile to another.
   *
   * @param e the {@link PlayerMovedEvent} containing movement details.
   */
  void onPlayerMoved(PlayerMovedEvent e);
}
