package edu.ntnu.idi.idatt.domain.factory.laddersgame;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.action.laddersgame.ResetAction;
import edu.ntnu.idi.idatt.domain.factory.NoDestinationTileActionFactory;

/**
 * Factory for creating {@link ResetAction}s that send the player
 * back to the start tile in a Snakes & Ladders game.
 *
 * <p>Implementations must always return a non-null {@code TileAction}.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class ResetActionFactory implements NoDestinationTileActionFactory {
  /**
   * Creates a new reset action which returns the player to the start tile.
   *
   * @return a {@link ResetAction} instance.
   */
  @Override
  public TileAction createTileAction() {
    return new ResetAction();
  }

  /**
   * Retrieves a unique identifier for this type of action.
   *
   * @return a string representing the action type.
   */
  @Override
  public String getActionType() {
    return ResetAction.actionType;
  }
}
