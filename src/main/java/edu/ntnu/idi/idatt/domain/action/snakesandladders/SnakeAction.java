package edu.ntnu.idi.idatt.domain.action.snakesandladders;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.Optional;

/**
 * Represents an action triggered when a {@link Player} lands on a snake tile.
 * This action causes the player to move to a lower-numbered tile.
 *
 * <br>
 * <b>Note:</b> The destination {@link Tile} must have a lower ID than the
 * player's current tile to be a valid snake action.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class SnakeAction implements TileAction {
  public static final String actionType = "SnakeAction";
  private final Tile destinationTile;

  /**
   * Constructs a {@code SnakeAction} with the specified destination tile.
   *
   * @param destinationTile is the tile to which the player will be moved.
   *
   * @throws IllegalArgumentException if the destination tile is {@code null}.
   */
  public SnakeAction(Tile destinationTile) {
    Validation.validateNonNull(destinationTile, "Destination tile");
    this.destinationTile = destinationTile;
  }

  /**
   * Moves the specified player to the destination tile, simulating sliding down a snake.
   *
   * @param player is the player who landed on the snake tile.
   *
   * @throws IllegalArgumentException if the player is {@code null}.
   * @throws IllegalStateException if the destination tile is higher than the current tile.
   */
  @Override
  public void perform(Player player) {
    Validation.validateNonNull(player, "Player");
    if (player.getCurrentTile() != null
          && player.getCurrentTile().getTileId() < destinationTile.getTileId()
    ) {
      throw new IllegalStateException("Player should not be able to climb up a snake.");
    }
    player.placeOnTile(destinationTile);
  }

  /**
   * Retrieves a string identifier describing the {@link SnakeAction}.
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
  public Optional<Tile> getDestinationTile() {
    return Optional.of(destinationTile);
  }
}