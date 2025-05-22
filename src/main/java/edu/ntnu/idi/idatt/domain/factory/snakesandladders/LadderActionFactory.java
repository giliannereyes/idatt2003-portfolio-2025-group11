package edu.ntnu.idi.idatt.domain.factory.snakesandladders;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.action.snakesandladders.LadderAction;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.factory.DestinationTileActionFactory;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * Factory for creating {@link LadderAction}s that transport the player
 * to a specified destination tile on a Snakes & Ladders board.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class LadderActionFactory implements DestinationTileActionFactory {
  /**
   * Creates a new ladder action that moves the player to {@code destinationTile}.
   *
   * @param destinationTile is the tile to which the ladder transports the player.
   *
   * @return a {@link LadderAction} configured with the given destination tile.
   *
   * @throws IllegalArgumentException if the {@code destinationTile} is null.
   */
  @Override
  public TileAction createTileAction(Tile destinationTile) {
    Validation.validateNonNull(destinationTile, "Destination tile");
    return new LadderAction(destinationTile);
  }

  /**
   * Retrieves a unique identifier for this type of action.
   *
   * @return a string representing the action type.
   */
  @Override
  public String getActionType() {
    return LadderAction.actionType;
  }
}
