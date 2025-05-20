package edu.ntnu.idi.idatt.domain.action.monopoly;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.Optional;

/**
 * Represents an action triggered when a {@link Player} is sent to jail in a Monopoly-style game.
 * This action moves the player to the jail tile and causes them to skip their next turn.
 *
 * <br>
 * <b>Note:</b> The player is only moved if not already on the jail tile.
 *
 * @version 0.1
 * @since 0.1
 */
public class GoToJailAction implements TileAction {
  public static final String actionType = "GoToJailAction";
  private final Tile jailTile;

  /**
   * Constructs a {@code GoToJailAction} with the specified jail tile.
   *
   * @param jailTile is the tile representing the jail location.
   *
   * @throws IllegalArgumentException if jail tile is {@code null}
   */
  public GoToJailAction(Tile jailTile) {
    Validation.validateNonNull(jailTile, "Jail tile");
    this.jailTile = jailTile;
  }

  /**
   * Moves the specified player to the jail tile (if not already there)
   * and sets their skip-turn status to {@code true}.
   *
   * @param player is the player being sent to jail.
   *
   * @throws IllegalArgumentException if player is {@code null}.
   */
  @Override
  public void perform(Player player) {
    Validation.validateNonNull(player, "Player");
    if (!player.getCurrentTile().equals(jailTile)) {
      player.placeOnTile(jailTile);
    }
    player.setSkipTurn(true);
  }

  /**
   * Returns a string identifier describing the {@link GoToJailAction}.
   *
   * @return a non-null string representing the action type.
   */
  @Override public String getActionType() {
    return actionType;
  }

  /**
   * Returns the jail tile to which the player will be moved.
   *
   * @return an {@link Optional} containing the jail tile
   */
  @Override
  public Optional<Tile> getDestinationTile() {
    return Optional.of(jailTile);
  }
}

