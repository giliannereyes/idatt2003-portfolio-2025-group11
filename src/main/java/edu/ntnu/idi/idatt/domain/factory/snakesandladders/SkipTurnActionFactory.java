package edu.ntnu.idi.idatt.domain.factory.snakesandladders;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.action.snakesandladders.SkipTurnAction;
import edu.ntnu.idi.idatt.domain.factory.NoDestinationTileActionFactory;

/**
 * Factory for creating {@link SkipTurnAction}s that cause a player
 * to lose their next turn.
 *
 * @version 0.2
 * @since 0.1
 * @author Trang Duong
 */
public class SkipTurnActionFactory implements NoDestinationTileActionFactory {
  /**
   * Creates a new skip-turn action which makes the player skip their next move.
   *
   * @return a {@link SkipTurnAction} instance.
   */
  @Override
  public TileAction createTileAction() {
    return new SkipTurnAction();
  }

  /**
   * Retrieves a unique identifier for this type of action.
   *
   * @return a string representing the action type.
   */
  @Override
  public String getActionType() {
    return SkipTurnAction.actionType;
  }
}
