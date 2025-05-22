package edu.ntnu.idi.idatt.domain.factory.monopoly;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.action.monopoly.GoToJailAction;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.factory.DestinationTileActionFactory;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * Factory for creating {@link GoToJailAction}s that send the player
 * to the jail tile and skip their next turn.
 *
 * @version 0.1
 * @author Gilianne Reyes
 */
public class GoToJailActionFactory implements DestinationTileActionFactory {
  /**
   * Creates a new {@link GoToJailAction} that sends the player to the jail tile.
   *
   * @param destinationTile is the tile representing the jail location.
   *
   * @return a new {@link GoToJailAction} instance.
   *
   * @throws IllegalArgumentException if {@code destinationTile} is null.
   */
  @Override
  public TileAction createTileAction(Tile destinationTile) {
    Validation.validateNonNull(destinationTile, "Jail tile");
    return new GoToJailAction(destinationTile);
  }

  /**
   * Retrieves a unique identifier for this type of action.
   *
   * @return a string representing the action type.
   */
  @Override
  public String getActionType() {
    return GoToJailAction.actionType;
  }
}
