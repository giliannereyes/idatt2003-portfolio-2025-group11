package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.model.entities.Board;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the configuration of a game. It is responsible for storing the
 * player configurations and the board configuration.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class GameConfig {
    List<PlayerConfig> playerConfigs;
    Board board;

    /**
     * Constructs a GameConfig instance.
     */
    public GameConfig() {
        playerConfigs = new ArrayList<>();
    }

    /**
     * Sets the list of player configurations.
     *
     * @param playerConfigs is the list of player configurations to set.
     *
     * @throws IllegalArgumentException if the player configurations are null.
     */
    public void setPlayerConfigs(List<PlayerConfig> playerConfigs) {
        Validation.validateNonNull(playerConfigs, "Player configurations");
        this.playerConfigs = playerConfigs;
    }

    /**
     * Sets the board configuration.
     *
     * @param board the board configuration to set.
     *
     * @throws IllegalArgumentException if the board configuration is null.
     */
    public void setBoard(Board board) {
        Validation.validateNonNull(board, "Board configuration");
        this.board = board;
    }

    /**
     * Retrieves the list of player configurations.
     *
     * @return the list of player configurations.
     */
    public List<PlayerConfig> getPlayerConfigs() {
        return playerConfigs;
    }

    /**
     * Retrieves the board configuration.
     *
     * @return the board configuration.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Checks if the game configuration is complete by
     * checking if the player and board configurations are set.
     *
     * @return true if the configuration is complete, false otherwise.
     */
    public boolean isComplete() {
        return playerConfigs != null && !playerConfigs.isEmpty() && board != null;
    }
}
