package edu.ntnu.idi.idatt.model.entities;

import edu.ntnu.idi.idatt.model.strategies.DefaultMovementStrategy;
import edu.ntnu.idi.idatt.model.strategies.MovementStrategy;

/**
 * Represents a player in the game.
 * Each player has a name and a position on the board.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 * @author Gilianne Reyes
 */
public class Player {
    private final String name;
    private Tile currentTile;
    private MovementStrategy movementStrategy;

    /**
     * Constructs a new Player with a given name and starting position.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player name cannot be null or empty.");
        }
        this.name = name;
        this.movementStrategy = new DefaultMovementStrategy();
    }

    /**
     * Gets the player's name.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the movement strategy of the player, which determines how the player moves.
     *
     * @param movementStrategy is the movement strategy to set.
     */
    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    /**
     * Places the player on a tile.
     *
     * @param tile is the tile to place the player on.
     */
    public void placeOnTile(Tile tile) {
        currentTile = tile;
    }

    /**
     * Moves the player a number of steps.
     *
     * @param steps is the number of steps to move.
     */
    public void move(int steps) {
        Tile destination = movementStrategy.determineDestination(currentTile, steps);
        placeOnTile(destination);
        destination.landPlayer(this);
    }

    /**
     * Checks if the player has a current tile.
     *
     * @return the current tile of the player.
     */
    public Tile getCurrentTile() {
        return currentTile;
    }
}
