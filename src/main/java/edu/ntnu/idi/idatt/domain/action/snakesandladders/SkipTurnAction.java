package edu.ntnu.idi.idatt.domain.action.snakesandladders;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * SnakeAction class is a class that represents the action of a tile with a skip-turn effect.
 * When a {@link Player} lands on a tile with a skip-turn effect,
 * the player will skip their next turn.
 *
 * <br>
 * <b>Note:</b> The player's skip-turn flag will be set to {@code true}.
 *
 * @version 0.2
 * @since 0.1
 * @author Trang Duong
 * @author Gilianne Reyes
 */
public class SkipTurnAction implements TileAction {
  public static final String actionType = "SkipTurnAction";

  /**
   * Applies the skip-turn effect to the specified player.
   *
   * @param player is the player who landed on the skip-turn tile.
   *
   * @throws IllegalArgumentException if the player is {@code null}.
   */
  public void perform(Player player) {
    Validation.validateNonNull(player, "Player");
    player.setSkipTurn(true);
  }

  /**
   * Retrieves a string identifier describing the {@link SkipTurnAction}.
   *
   * @return a non-null string representing the action type.
   */
  @Override
  public String getActionType() {
    return actionType;
  }
}