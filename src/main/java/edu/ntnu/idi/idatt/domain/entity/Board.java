package edu.ntnu.idi.idatt.domain.entity;

import edu.ntnu.idi.idatt.domain.action.TileAction;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The {@code Board} class represents a game board consisting of tiles organized
 * in a grid format. It manages a collection of {@link Tile}, and supports adding, retrieving,
 * and interacting with these. The board also provides functionality to place players
 * on the start tile and to set actions for specific tiles.
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
  private Tile startTile;

  /**
   * Constructs a board of the given dimensions.
   * The board is initialized with no tiles and a default name and description.
   *
   * @param rows is the number of rows.
   * @param columns is the number of columns.
   * @throws IllegalArgumentException if {@code rows} or {@code columns} is not positive.
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
   * Adds a tile to the board's collection of tiles.
   *
   * @param tile is the tile to add.
   *
   * @throws IllegalArgumentException if the tile is null.
   * @throws IllegalArgumentException if a tile with the same ID is already a part of the board.
   */
  public void addTile(Tile tile) {
    Validation.validateNonNull(tile, "Tile");
    if (tiles.containsKey(tile.getTileId())) {
      throw new IllegalArgumentException("Tile with the same ID already exists.");
    }
    tiles.put(tile.getTileId(), tile);
  }

  /**
   * Retrieves a tile by its id.
   *
   * @param tileId is the id of the tile to get.
   *
   * @return is the tile with the given id or {@code null} if not found.
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
   * Sets the start tile of the board.
   *
   * <p>The provided tile must be a part of the board's tile collection. It is
   * also where players will start their game.</p>
   *
   * @param startTile the tile to be set as the start tile.
   *
   * @throws IllegalArgumentException if the start tile is null.
   * @throws IllegalArgumentException if the start tile is not part of the board.
   */
  public void setStartTile(Tile startTile) {
    Validation.validateNonNull(startTile, "Start tile");
    if (!tiles.containsValue(startTile)) {
      throw new IllegalArgumentException("Start tile must be a part of the board.");
    }
    this.startTile = startTile;
  }

  /**
   * Places a player on the start tile of the board.
   *
   * @param player is the player to be placed on the start tile.
   *
   * @throws IllegalArgumentException if the player is null.
   * @throws IllegalStateException if the start tile is null.
   */
  public void placePlayerOnStartTile(Player player) {
    Validation.validateNonNull(player, "Player");
    Tile startTile = getStartTile();
    if (startTile == null) {
      throw new IllegalStateException(
            "Unable to place player on start tile. Start tile is not set."
      );
      player.placeOnTile(startTile);
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
     * @return an {@link Optional} containing the next {@link Tile}, or empty if the current tile is the last one.
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
   * Retrieves the start tile of the board.
   *
   * @return the start tile of the board, or {@code null} if not set.
   */
  public Tile getStartTile() {
    return startTile;
  }

  /**
   * Adds an action to a specific tile.
   *
   * @param tileId is the id of the tile to add an action to.
   * @param tileAction is the action to add to the tile.
   *
   * @throws IllegalArgumentException if the tile action provided is null.
   * @throws IllegalArgumentException if the tile with the given id is not found.
   */
  public void addTileAction(int tileId, TileAction tileAction) {
    Validation.validateNonNull(tileAction, "Tile action");
    Tile tile = getTile(tileId);
    Validation.validateNonNull(tile, "Tile to add action to");
    tile.setLandAction(tileAction);
  }

  /**
   * Sets the description of the board.
   *
   * @param description is the description of the board.
   *
   * @throws IllegalArgumentException if the description is null or empty.
   */
  public void setDescription(String description) {
    Validation.validateNonEmptyStr(description, "Board description");
    this.description = description;
  }

  /**
   * Sets the name of the board.
   *
   * @param name is the name of the board.
   *
   * @throws IllegalArgumentException if the name is null or empty.
   */
  public void setName(String name) {
    Validation.validateNonEmptyStr(name, "Board name");
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
