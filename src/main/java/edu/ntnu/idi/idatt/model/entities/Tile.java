package edu.ntnu.idi.idatt.model.entities;

import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.utils.Validation;

import java.util.Optional;

/**
 * Class representing a tile on the board.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class Tile {
  private Tile nextTile;
  private final int tileId;
  private TileAction landAction;

  /**
   * Constructs a Tile instance.
   *
   * @param tileId is the id of the tile.
   *
   * @throws IllegalArgumentException if the tile id is negative.
   */
  public Tile(int tileId) {
    Validation.validateNonNegativeNum(tileId, "Tile id");
    this.tileId = tileId;
  }

  /**
   * Places a player on this tile and performs the action of this tile.
   *
   * @param player is the player that lands on this tile.
   *
   * @throws IllegalArgumentException if the player is null.
   */
  public void landPlayer(Player player) {
    Validation.validateNonNull(player, "Player");
    if (landAction != null) {
      landAction.perform(player);
    }
  }

  /**
   * Sets the action to be performed when a player lands on this tile.
   *
   * @param landAction is the action to be performed.
   */
  public void setLandAction(TileAction landAction) {
    this.landAction = landAction;
  }

  /**
   * Retrieves the action assigned to this tile.
   *
   * @return an {@link Optional} containing the action assigned to this tile.
   */
  public Optional<TileAction> getLandAction() {
    return Optional.ofNullable(landAction);
  }

  /**
   * Sets the next tile of this tile.
   *
   * @param nextTile is the next tile.
   */
  public void setNextTile(Tile nextTile) {
    this.nextTile = nextTile;
  }

  /**
   * Gets the id of this tile.
   *
   * @return the id of this tile.
   */
  public int getTileId() {
    return tileId;
  }

  /**
   * Checks if this tile is the last tile.
   *
   * @return the id of this tile.
   */
  public boolean isLastTile() {
    return nextTile == null;
  }

  /**
   * Retrieves the next tile of this tile.
   *
   * @return an {@link Optional} containing the next tile.
   */
  public Optional<Tile> getNextTile() {
    return Optional.ofNullable(nextTile);
  }
}
