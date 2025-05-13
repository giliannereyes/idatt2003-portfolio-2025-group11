package edu.ntnu.idi.idatt.domain.entity;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.utils.Validation;

import java.util.Optional;

/**
 * Class representing a tile on the board.
 *
 * @version 0.3
 * @since 0.1
 * @author Gilianne Reyes
 */
public class Tile {
  private Tile nextTile;
  private final int tileId;
  private TileAction landAction;
  private final double x;
  private final double y;

  /**
   * Constructs a Tile instance.
   *
   * @param tileId is the id of the tile.
   * @param x is the x-coordinate of the tile.
   * @param y is the y-coordinate of the tile.
   *
   * @throws IllegalArgumentException if the tile id is negative.
   */
  public Tile(int tileId, double x, double y) {
    Validation.validateNonNegativeNum(tileId, "Tile id");
    Validation.validateNonNegativeNum(x, "X-coordinate");
    Validation.validateNonNegativeNum(y, "Y-coordinate");
    this.tileId = tileId;
    this.x = x;
    this.y = y;
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

  /**
   * Retrieves the x-coordinate of the tile.
   *
   * @return the x-coordinate of the tile.
   */
  public double getX() {
    return x;
  }

  /**
   * Retrieves the y-coordinate of the tile.
   *
   * @return the y-coordinate of the tile.
   */
  public double getY() {
    return y;
  }
}
