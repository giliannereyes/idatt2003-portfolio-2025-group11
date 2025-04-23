package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.utils.Validation;

/**
 * Represents the configuration of a player in the game. It
 * is responsible for storing the player and the corresponding
 * token image path.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class PlayerConfig {
    Player player;
    String tokenImagePath;

    /**
     * Constructs a PlayerConfig instance.
     *
     * @param player is the player to configure.
     * @param tokenImagePath is the path to the token image.
     */
    public PlayerConfig(Player player, String tokenImagePath) {
        Validation.validateNonNull(player, "Player object");
        Validation.validateNonEmptyStr(tokenImagePath, "Token image path");
        this.player = player;
        this.tokenImagePath = tokenImagePath;
    }

    /**
     * Retrieves the player object of this configuration.
     *
     * @return the player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Retrieves the path to the token image representing
     * the player in this configuration.
     *
     * @return the path to the token image.
     */
    public String getTokenImagePath() {
        return tokenImagePath;
    }
}
