package edu.ntnu.idi.idatt.model.entities;

/**
 * Represents a player in the game.
 * Each player has a name, a position on the board, and a flag indicating whether they must skip their next turn.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class Player {
    private String name;
    private int position;
    private boolean skipNextTurn;

    /**
     * Constructs a new Player with a given name and starting position.
     *
     * @param name      The name of the player.
     * @param position  The starting position of the player (usually 0).
     */
    public Player(String name, int position) {
        this.name = name;
        this.position = 0;
        this.skipNextTurn = false;
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
     * Gets the current position of the player on the board.
     *
     * @return The player's current position.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Moves the player to a new position on the board.
     *
     * @param newPosition The new position the player moves to.
     */
    public void move(int newPosition) {
        this.position = newPosition;
    }

    /**
     * Sets whether the player should skip their next turn.
     *
     * @param skip {@code true} if the player must skip their next turn, {@code false} otherwise.
     */
    public void setSkipNextTurn(boolean skip) {
        this.skipNextTurn = skip;
    }

    /**
     * Checks if the player must skip their next turn.
     *
     * @return {@code true} if the player should skip their next turn, {@code false} otherwise.
     */
    public boolean shouldSkipNextTurn() {
        return skipNextTurn;
    }
}
