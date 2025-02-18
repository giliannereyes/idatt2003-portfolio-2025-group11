package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.entities.Board;
import edu.ntnu.idi.idatt.model.entities.Dice;
import edu.ntnu.idi.idatt.model.entities.Player;

import java.util.List;

/**
 * Abstract class for board games. Contains a board, a list of players, a dice, and a winner.
 * Provides methods for setting up and playing the game.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public abstract class BoardGame {
    protected Board board;
    protected List<Player> players;
    protected Dice dice;
    protected Player winner;
    protected int currentPlayerIndex = 0;

    /**
     * Constructs a BoardGame instance.
     *
     * @param board is the board of the game.
     * @param players is the list of players in the game.
     * @param dice is the dice used in the game.
     */
    public BoardGame(Board board, List<Player> players, Dice dice) {
        this.board = board;
        this.players = players;
        this.dice = dice;
    }

    /**
     * Sets up the game by placing all players on the start tile.
     */
    public void setUpGame() {
        for (Player player : players) {
            board.placePlayerOnStartTile(player);
        }
    }

    /**
     * Implements the main game loop. Iterates turns until the game is over.
     */
    public void playGame() {
        while (!isGameOver()) {
            for (Player player : players) {
                playTurn(player);
                if (isGameOver()) {
                    winner = player;
                }
            }
        }
    }

    /**
     * Gets the player who is next in line to play.
     *
     * @return the player who is next in line to play.
     */
    protected Player getNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(currentPlayerIndex);
    }

    /**
     * Rolls the dice.
     *
     * @return the result of the dice roll.
     */
    protected int rollDice() {
        return dice.roll();
    }

    /**
     * Plays a turn for a player.
     *
     * @param player is the player taking their turn.
     */
    protected abstract void playTurn(Player player);

    /**
     * Checks if the game is over.
     *
     * @return {@code true} if the game is over, {@code false} otherwise.
     */
    protected abstract boolean isGameOver();
}
