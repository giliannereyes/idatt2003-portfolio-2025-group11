package edu.ntnu.idi.idatt.domain.game;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Dice;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.common.PlayerWonEvent;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.*;

/**
 * Abstract class for board games. Contains a board, a list of players, a dice, and a winner.
 * Provides methods for setting up and playing the game.
 *
 * @version 0.3
 * @since 0.1
 * @author Gilianne Reyes
 */
public abstract class BoardGame {
    protected Board board;
    protected List<Player> players;
    protected Dice dice;
    protected Player winner;
    protected int currentPlayerIndex = 0;
    protected EventBus eventBus;

    /**
     * Constructs a BoardGame instance.
     *
     * @param board is the board of the game.
     * @param players is the list of players in the game.
     * @param dice is the dice used in the game.
     * @param eventBus is the event bus used in the game.
     *
     * @throws IllegalArgumentException if any of the parameters is null.
     */
    public BoardGame(Board board, List<Player> players, Dice dice, EventBus eventBus) {
        Validation.validateNonNull(board, "Board");
        Validation.validateNonNull(players, "Players");
        Validation.validateNonNull(dice, "Dice");
        Validation.validateNonNull(eventBus, "Event bus");
        this.board = board;
        this.players = players;
        this.dice = dice;
        this.eventBus = eventBus;
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
     * Plays the next turn in the game.
     */
    public void playNextTurn() {
        if (isGameOver()) {
            eventBus.publish(new PlayerWonEvent(winner));
        } else {
            Player currentPlayer = players.get(currentPlayerIndex);
            if (currentPlayer.willSkipTurn()) {
                currentPlayer.setSkipTurn(false);
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                playNextTurn();
            } else {
                playTurn(currentPlayer);
                if (isGameOver()) {
                    eventBus.publish(new PlayerWonEvent(winner));
                } else {
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
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
     * Retrieves the event bus of the game.
     *
     * @return the event bus of the game.
     */
    protected EventBus getEventBus() {
        return eventBus;
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
