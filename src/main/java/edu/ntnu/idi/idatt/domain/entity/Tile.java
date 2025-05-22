package edu.ntnu.idi.idatt.domain.entity;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.Optional;

/**
 * Represents a tile on a game board. Each tile has a unique id, coordinates
 * (x, y), and optionally an action that can be performed when a player lands on it.
 *
 * @version 0.3
 * @since 0.1
 * @author Gilianne Reyes
 */
public class Tile {
  private Tile nextTile;
  private final int tileId;
  private TileAction landAction;
  private final double xCoordinate;
  private final double yCoordinate;

  /**
   * Constructs a Tile instance.
   *
   * @param tileId is the id of the tile.
   * @param x is the x-coordinate of the tile.
   * @param y is the y-coordinate of the tile.
   *
   * @throws IllegalArgumentException if the tile id or any coordinate is negative.
   */
  public Tile(int tileId, double x, double y) {
    Validation.validateNonNegativeNum(tileId, "Tile id");
    Validation.validateNonNegativeNum(x, "X-coordinate");
    Validation.validateNonNegativeNum(y, "Y-coordinate");
    this.tileId = tileId;
    this.xCoordinate = x;
    this.yCoordinate = y;
  }

  /**
   * Performs the action associated with this tile when a player lands on it,
   * if any.
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
   *
   * @throws IllegalArgumentException if the action is null.
   */
  public void setLandAction(TileAction landAction) {
    Validation.validateNonNull(landAction, "Tile land action");
    this.landAction = landAction;
  }

  /**
   * Retrieves the action assigned to this tile.
   *
   * @return an {@link Optional} containing the action assigned to this tile,
   *         or an empty {@link Optional} if no action is assigned.
   */
  public Optional<TileAction> getLandAction() {
    return Optional.ofNullable(landAction);
  }

  /**
   * Sets the next tile of this tile. If the next tile is {@code null},
   * this tile is considered the last tile in the sequence.
   *
   * @param nextTile is the next tile.
   */
  public void setNextTile(Tile nextTile) {
    this.nextTile = nextTile;
  }

  /**
   * Retrieves the id of this tile.
   *
   * @return the id of this tile.
   */
  public int getTileId() {
    return tileId;
  }

  /**
   * Checks if this tile is the last tile in the sequence.
   *
   * @return {@code true} if this tile is the last tile, {@code false} otherwise.
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

  /**
   * Retrieves the x-coordinate of the tile.
   *
   * @return the x-coordinate of the tile.
   */
  public double getX() {
    return xCoordinate;
  }

  /**
   * Retrieves the y-coordinate of the tile.
   *
   * @return the y-coordinate of the tile.
   */
  public double getY() {
    return yCoordinate;
  }
}
