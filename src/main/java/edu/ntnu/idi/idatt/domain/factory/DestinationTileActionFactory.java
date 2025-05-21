package edu.ntnu.idi.idatt.domain.factory;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Tile;

/**
 * Factory interface for creating {@link TileAction}s that
 * require a specific destination tile.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface DestinationTileActionFactory {
  /**
   * Creates a tile action based on the destination tile.
   *
   * @param destinationTile is the tile the player is placed on with this action.
   *
   * @return an instance of the tile action.
   */
  TileAction createTileAction(Tile destinationTile);

  /**
   * Retrieves a unique identifier for this type of action.
   *
   * @return a string representing the action type.
   */
  String getActionType();
}
