package edu.ntnu.idi.idatt.model.entities;

import edu.ntnu.idi.idatt.model.actions.*;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Board class represents the game board. It contains a map of tiles and a start tile,
 * and provides methods to add tiles to the board, get a tile by its id, initialize the board,
 * and place players on the board.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class Board {
    private final Map<Integer, Tile> tiles;
    private String description;
    private String name;

    /**
     * Constructs the board and initializes the tiles map.
     */
    public Board() {
        tiles = new LinkedHashMap<>();
        name = "Unnamed Board";
        description = "No description available.";
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
     * Sets up the board with the given number of tiles and special effects
     * like ladder, snake, skip-turn and step-back.
     *
     * @param tileCount is the number of tiles to set up.
     *
     * @throws IllegalArgumentException if the tile count is not positive.
     */
    public void initializeBoard(int tileCount) {
        Validation.validatePositiveNum(tileCount, "Tile count");
        // Tile previousTile = startTile; // Special entry point tile
        Tile previousTile = new Tile(1);
        addTile(previousTile);
        for (int i = 2; i <= tileCount; i++) {
            Tile tile = new Tile(i);
            previousTile.setNextTile(tile);
            addTile(tile);
            previousTile = tile;
        }
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
}
