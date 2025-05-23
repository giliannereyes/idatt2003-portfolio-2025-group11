package edu.ntnu.idi.idatt.domain.factory;

import edu.ntnu.idi.idatt.domain.action.TileAction;

/**
 * Factory interface for creating {@link TileAction}s that
 * do not require a destination tile.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface NoDestinationTileActionFactory {
  /**
   * Creates a tile action without a destination tile.
   *
   * @return an instance of the tile action.
   */
  TileAction createTileAction();

  /**
   * Retrieves a unique identifier for this type of action.
   *
   * @return a string representing the action type.
   */
  String getActionType();
}
