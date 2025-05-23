package edu.ntnu.idi.idatt.domain.event.common;

/**
 * Listener interface for handling {@link DiceRolledEvent}s.
 *
 * <p>Implementations of this interface will be notified whenever
 * a dice roll occurs in the game.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface DiceRolledListener {
  /**
   * Called when a dice roll event occurs.
   *
   * @param e the {@link DiceRolledEvent} carrying the roll values; must not be null
   * @throws NullPointerException if {@code e} is null
   */
  void onDiceRolled(DiceRolledEvent e);
}
