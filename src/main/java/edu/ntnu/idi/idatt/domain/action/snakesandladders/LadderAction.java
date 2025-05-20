package edu.ntnu.idi.idatt.domain.action.snakesandladders;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.Optional;

/**
 * Represents an action triggered when a {@link Player} lands on a ladder tile.
 * This action moves the player forward to a specified destination tile.
 *
 * <br>
 * <b>Note:</b> The destination {@link Tile} must have a higher ID than the
 * player's current tile to be a valid ladder action.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class LadderAction implements TileAction {
  public static final String actionType = "LadderAction";
  private final Tile destinationTile;

  /**
   * Constructs a {@code LadderAction} with the specified destination tile.
   *
   * @param destinationTile is the tile the player will move to when this action is performed.
   *
   * @throws IllegalArgumentException if the destination tile is {@code null}.
   */
  public LadderAction(Tile destinationTile) {
    Validation.validateNonNull(destinationTile, "Destination tile");
    this.destinationTile = destinationTile;
  }

  /**
   * Moves the specified player to the destination tile, simulating climbing a ladder.
   *
   * @param player is the player who landed on the ladder tile
   *
   * @throws IllegalArgumentException if the player is {@code null}.
   * @throws IllegalStateException if the destination tile is lower than the current tile.
   */
  @Override
  public void perform(Player player) {
    Validation.validateNonNull(player, "Player");
    if (player.getCurrentTile() != null
          && player.getCurrentTile().getTileId() > destinationTile.getTileId()
    ) {
      throw new IllegalStateException("Player should not be able to climb down a ladder.");
    }
    player.placeOnTile(destinationTile);
  }

  /**
   * Retrieves a string identifier describing the {@link LadderAction}.
   *
   * @return a non-null string representing the action type.
   */
  public String getActionType() {
    return actionType;
  }

  /**
   * Retrieves the destination tile of the action.
   *
   * @return an {@link Optional} containing the destination tile.
   */
  @Override
  public Optional<Tile> getDestinationTile() {
    return Optional.of(destinationTile);
  }
}
