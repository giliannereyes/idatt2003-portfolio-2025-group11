package edu.ntnu.idi.idatt.domain.entity;

import edu.ntnu.idi.idatt.domain.action.*;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The Board class represents the game board. It contains a map of tiles and a start tile,
 * and provides methods to add tiles to the board, get a tile by its id, initialize the board,
 * and place players on the board.
 *
 * @version 0.3
 * @since 0.1
 * @author Gilianne Reyes
 */
public class Board {
  private final Map<Integer, Tile> tiles;
  private String description;
  private String name;
  private final int rows;
  private final int columns;

  /**
   * Constructs the board and initializes the tiles map.
   */
  public Board(int rows, int columns) {
    Validation.validatePositiveNum(rows, "Row count");
    Validation.validatePositiveNum(columns, "Column count");
    tiles = new LinkedHashMap<>();
    name = "Unnamed Board";
    description = "No description available.";
    this.rows = rows;
    this.columns = columns;
  }

  /**
   * Adds a tile to the board.
   *
   * @param tile is the tile to add.
   *
   * @throws IllegalArgumentException if the tile is null.
   */
  public void addTile(Tile tile) {
    Validation.validateNonNull(tile, "Tile");
    tiles.put(tile.getTileId(), tile);
  }

  /**
   * Gets a tile by its id.
   *
   * @param tileId is the id of the tile to get.
   *
   * @return is the tile with the given id.
   */
  public Tile getTile(int tileId) {
    return tiles.get(tileId);
  }

  /**
   * Retrieves the tiles on the board.
   *
   * @return an unmodifiable map of the tiles on the board.
   */
  public Map<Integer, Tile> getTiles() {
    return Collections.unmodifiableMap(tiles);
  }

  /**
   * Places a player on the start tile of the board.
   *
   * @param player is the player to place on the start tile.
   *
   * @throws IllegalArgumentException if the player is null.
   */
  public void placePlayerOnStartTile(Player player) {
    Validation.validateNonNull(player, "Player");
    player.placeOnTile(getTile(1));
  }

  /**
   * Retrieves the start tile of the board.
   *
   * @return the start tile of the board.
   */
  public Tile getStartTile() {
    return getTile(1);
  }


  /**
   * Retrieves the last tile in the grid based on the total number of rows and columns.
   * It also checks if there is a next tile after the last one and logs a message if none exists.
   *
   * @return the last {@link Tile} in the grid.
   */
  public Tile getLastTile() {
    Tile lastTile = getTile(rows * columns);
    Optional<Tile> nextTile = getNextTile(lastTile);
    if (nextTile.isEmpty()) {
      System.out.println("No next tile exists. This is the last tile.");
    }
    return lastTile;
  }

  /**
   * Returns the next tile in the grid based on the current tile's ID.
   * If the current tile is the last one, it returns an empty {@link Optional}.
   *
   * @param currentTile the current {@link Tile} from which to find the next tile.
   * @return an {@link Optional} containing the next {@link Tile},
   * or empty if the current tile is the last one.
   */
  public Optional<Tile> getNextTile(Tile currentTile) {
    int currentTileId = currentTile.getTileId();
    int nextTileId = currentTileId + 1;

    if (nextTileId > rows * columns) {
      return Optional.empty();
    } else {
      return Optional.of(getTile(nextTileId));
    }
  }

  /**
   * Adds an action to a specific tile.
   *
   * @param tileId is the id of the tile to add an action to.
   * @param tileAction is the action to add to the tile.
   *
   * @throws IllegalArgumentException if the tile action or tile is null.
   */
  public void addTileAction(int tileId, TileAction tileAction) {
    Validation.validateNonNull(tileAction, "Tile action");
    Tile tile = getTile(tileId);
    Validation.validateNonNull(tile, "Tile");
    tile.setLandAction(tileAction);
  }

  /**
   * Sets the description of the board.
   *
   * @param description is the description of the board.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Retrieves the description of the board.
   *
   * @param name is the name of the board.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Retrieves the description of the board.
   *
   * @return the description of the board.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Retrieves the name of the board.
   *
   * @return the name of the board.
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the number of rows of the board.
   *
   * @return the number of rows of the board.
   */
  public int getRows() {
    return rows;
  }

  /**
   * Retrieves the number of columns of the board.
   *
   * @return the number of columns of the board.
   */
  public int getColumns() {
    return columns;
  }
}
