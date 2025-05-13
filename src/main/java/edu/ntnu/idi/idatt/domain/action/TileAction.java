package edu.ntnu.idi.idatt.domain.action;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;

import java.util.Optional;

/**
 * Interface for actions that can be performed on a tile.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface TileAction {
  /**
   * Performs the action on the player.
   *
   * @param player is the player that landed on the tile.
   */
  void perform(Player player);

  /**
   * Retrieves the type of the action.
   *
   * @return the type of the action.
   */
  String getActionType();

  /**
   * Retrieves the destination tile of the action.
   *
   * @return an {@link Optional} containing the destination
   * tile of the action, which is empty by default.
   */
  default Optional<Tile> getDestinationTile() {
    return Optional.empty();
  }
}
