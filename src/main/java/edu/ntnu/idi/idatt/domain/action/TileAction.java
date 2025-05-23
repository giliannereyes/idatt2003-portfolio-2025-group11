package edu.ntnu.idi.idatt.domain.action;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import java.util.Optional;

/**
 * Represents a game-related action that can be performed when a player lands on a specific tile.
 * Implementations define behavior such as moving the player or managing their turn.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface TileAction {
  /**
   * Executes the defined action on the specified player.
   *
   * @param player is the player who triggered the action by landing on the tile.
   */
  void perform(Player player);

  /**
   * Retrieves a string identifier describing the type of this tile action.
   *
   * @return a non-null string representing the action type.
   */
  String getActionType();

  /**
   * Returns the destination tile associated with this action, if any.
   *
   * @return an {@link Optional} containing the destination {@link Tile},
   *         or {@link Optional#empty()} if the action does not involve movement.
   */
  default Optional<Tile> getDestinationTile() {
    return Optional.empty();
  }
}
