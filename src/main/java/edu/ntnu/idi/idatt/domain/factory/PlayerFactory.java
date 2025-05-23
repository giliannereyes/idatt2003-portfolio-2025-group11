package edu.ntnu.idi.idatt.domain.factory;

import edu.ntnu.idi.idatt.domain.entity.Player;

/**
 * A factory interface for creating Player objects.
 *
 * <p>This interface defines a method for creating Player instances.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
@FunctionalInterface
public interface PlayerFactory {
  /**
   * Creates a new Player instance with the specified name.
   *
   * @param playerName the name of the player.
   *
   * @return a new Player instance.
   */
  Player createPlayer(String playerName);
}

