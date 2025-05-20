package edu.ntnu.idi.idatt.domain.entity;

import edu.ntnu.idi.idatt.domain.strategy.DefaultMovementStrategy;
import edu.ntnu.idi.idatt.domain.strategy.MovementStrategy;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * Represents a player in the game.
 * Each player has a name, a position on the board, and a movement strategy
 * that determines how they move on the board.
 *
 * @version 0.3
 * @since 0.1
 * @author Trang Duong
 * @author Gilianne Reyes
 */
public class Player {
  private final String name;
  private Tile currentTile;
  private Tile startTile;
  private MovementStrategy movementStrategy;
  private boolean skipTurn;

  /**
   * Constructs a new Player instance with the given name.
   * The player's movement strategy is set to the default strategy.
   *
   * @param name is the name of the player.
   *
   * @throws IllegalArgumentException if the name is empty or null.
   */
  public Player(String name) {
    Validation.validateNonEmptyStr(name, "Name");
    this.name = name;
    this.movementStrategy = new DefaultMovementStrategy();
  }

  /**
   * Retrieves the name of the player.
   *
   * @return the name of the player.
   */
  public String getName() {
    return name;
  }

  /**
   * Checks if the player will skip their turn.
   *
   * @return {@code true} if the player will skip their turn, {@code false} otherwise.
   */
  public boolean willSkipTurn() {
    return skipTurn;
  }

  /**
   * Sets whether the player will skip their turn.
   *
   * @param skipTurn {@code true} if the player will skip their turn, {@code false} otherwise.
   */
  public void setSkipTurn(boolean skipTurn) {
    this.skipTurn = skipTurn;
  }

  /**
   * Sets the movement strategy of the player, which determines how the player moves.
   *
   * @param movementStrategy is the movement strategy to set.
   *
   * @throws IllegalArgumentException if the movement strategy is null.
   */
  public void setMovementStrategy(MovementStrategy movementStrategy) {
    Validation.validateNonNull(movementStrategy, "Movement strategy");
    this.movementStrategy = movementStrategy;
  }

  /**
   * Places the player on a tile. If this is the first time the player is placed,
   * the tile is also set as the player's start tile.
   *
   * @param tile is the tile to place the player on.
   *
   * @throws IllegalArgumentException if the tile is null.
   */
  public void placeOnTile(Tile tile) {
    Validation.validateNonNull(tile, "Tile");
    if (currentTile == null) {
      startTile = tile;
    }
    currentTile = tile;
  }

  /**
   * Moves the player forward by the given number of steps, using the
   * configured {@link MovementStrategy}.
   *
   * @param steps is the number of steps to advance-
   *
   * @throws IllegalArgumentException if the number of steps is negative.
   * @throws IllegalStateException if the player has not yet been placed on a tile.
   */
  public void move(int steps) {
    Validation.validateNonNegativeNum(steps, "Steps");
    if (currentTile == null) {
      throw new IllegalStateException("Cannot move before being placed on a tile");
    }
    Tile destination = movementStrategy.determineDestination(currentTile, steps);
    placeOnTile(destination);
  }

  /**
   * Retrieves the current tile of the player.
   *
   * @return the current tile of the player, or {@code null} if not set.
   */
  public Tile getCurrentTile() {
    return currentTile;
  }

  /**
   * Retrieves the start tile of the player.
   *
   * @return the start tile of the player, or {@code null} if not set.
   */
  public Tile getStartTile() {
    return startTile;
  }
}
