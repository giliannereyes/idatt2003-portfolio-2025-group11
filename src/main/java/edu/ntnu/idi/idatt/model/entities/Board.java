package edu.ntnu.idi.idatt.model.entities;

import edu.ntnu.idi.idatt.model.actions.TileAction;

import java.util.HashMap;
import java.util.Map;

/**
 * The Board class represents the game board. It contains a map of tiles and a start tile,
 * and provides methods to add tiles to the board, get a tile by its id, initialize the board,
 * and place players on the board.
 *
 * @version 0.1
 * @since 0.1
 * @author GIlianne Reyes
 */
public class Board {
    private final Map<Integer, Tile> tiles;
    private final Tile startTile = new Tile(0);

    /**
     * Constructs the board and initializes the tiles map.
     */
    public Board() {
        tiles = new HashMap<>();
    }

    /**
     * Adds a tile to the board.
     *
     * @param tile is the tile to add.
     */
    public void addTile(Tile tile) {
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
     * Sets up the board with the given number of tiles.
     *
     * @param tileCount is the number of tiles to set up.
     */
    public void initializeBoard(int tileCount) {
        Tile previousTile = startTile; // Special entry point tile
        for (int i = 1; i <= tileCount; i++) {
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
     */
    public void placePlayerOnStartTile(Player player) {
        player.placeOnTile(startTile);
    }

    /**
     * Adds an action to a specific tile.
     *
     * @param tileId is the id of the tile to add an action to.
     * @param tileAction is the action to add to the tile.
     */
    public void addTileAction(int tileId, TileAction tileAction) {
        Tile tile = getTile(tileId);
        tile.setLandAction(tileAction);
    }
}
