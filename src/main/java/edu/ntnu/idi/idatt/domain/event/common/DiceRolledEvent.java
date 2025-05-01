package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

/**
 * Represents an event where a player rolls the dice.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class DiceRolledEvent implements GameEvent {
    private final Player player;
    private final int roll1;
    private final int roll2;

    /**
     * Constructs a DiceRolledEvent instance.
     *
     * @param player is the player that rolled the dice.
     * @param roll1 is the number rolled on the first die.
     * @param roll2 is the number rolled on the second die.
     */
    public DiceRolledEvent(Player player, int roll1, int roll2) {
        this.player = player;
        this.roll1 = roll1;
        this.roll2 = roll2;
    }

    /**
     * Retrieves the player that rolled the dice.
     *
     * @return the player that rolled the dice.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Retrieves the number rolled on the first die.
     */
    public int getRoll1() {
        return roll1;
    }

    /**
     * Retrieves the number rolled on the second die.
     */
    public int getRoll2() {
        return roll2;
    }
}
