package edu.ntnu.idi.idatt.model.entities;

import edu.ntnu.idi.idatt.model.actions.TileAction;

/**
 * Class representing a tile on the board.
 *
 * @version 0.1
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
   */
  public Tile(int tileId) {
    this.tileId = tileId;
  }

  /**
   * Places a player on this tile and performs the action of this tile.
   *
   * @param player is the player that lands on this tile.
   */
  public void landPlayer(Player player) {
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
   * Gets the next tile of this tile.
   *
   * @return the next tile of this tile.
   */
  public Tile getNextTile() {
    return nextTile;
  }

  /**
   * Called when a player leaves this tile.
   *
   * @param player is the player that leaves this tile.
   */
  public void leavePlayer(Player player) {
  }
}
