package edu.ntnu.idi.idatt.domain.action.laddersgame;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * Represents an action triggered when a {@link Player} lands on a reset tile.
 * This action causes the player to return to their start tile.
 *
 * <br>
 * <b>Note:</b> The player must have a defined start tile.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class ResetAction implements TileAction {
  public static final String actionType = "ResetAction";

  /**
   * Moves the specified player back to their start tile, simulating a reset.
   *
   * @param player is the player who landed on the reset tile.
   *
   * @throws IllegalArgumentException if the player is {@code null}.
   * @throws IllegalStateException if the player's start tile is {@code null}.
   */
  @Override
  public void perform(Player player) {
    Validation.validateNonNull(player, "Player");
    Tile startTile = player.getStartTile();
    player.placeOnTile(startTile);
  }

  /**
   * Retrieves a string identifier describing the {@link ResetAction}.
   *
   * @return a non-null string representing the action type.
   */
  @Override
  public String getActionType() {
    return actionType;
  }
}
