package edu.ntnu.idi.idatt.domain.factory.laddersgame;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.action.laddersgame.SnakeAction;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.factory.DestinationTileActionFactory;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * Factory for creating {@link SnakeAction} that move the player
 * down to a specified destination tile.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class SnakeActionFactory implements DestinationTileActionFactory {
  /**
   * Creates a new snake action which transports the player to {@code destinationTile}.
   *
   * @param destinationTile the tile to which the snake transports the player.
   *
   * @return a {@link SnakeAction} instance.
   *
   * @throws IllegalArgumentException if {@code destinationTile} is null.
   */
  @Override
  public TileAction createTileAction(Tile destinationTile) {
    Validation.validateNonNull(destinationTile, "Destination tile");
    return new SnakeAction(destinationTile);
  }

  /**
   * Retrieves a unique identifier for this type of action.
   *
   * @return a string representing the action type.
   */
  @Override
  public String getActionType() {
    return SnakeAction.actionType;
  }
}
