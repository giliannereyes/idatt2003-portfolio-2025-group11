package edu.ntnu.idi.idatt.model.events.types;

import edu.ntnu.idi.idatt.model.entities.Player;

/**
 * Represents an event where a player rolls the dice.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class DiceRolledEvent implements GameEvent {
    private final Player player;
    private final int roll;

    /**
     * Constructs a DiceRolledEvent instance.
     *
     * @param player is the player that rolled the dice.
     * @param roll is the number rolled on the dice.
     */
    public DiceRolledEvent(Player player, int roll) {
        this.player = player;
        this.roll = roll;
    }

    /**
     * Gets the player that rolled the dice.
     *
     * @return the player that rolled the dice.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the number rolled on the dice.
     */
    public int getRoll() {
        return roll;
    }
}
