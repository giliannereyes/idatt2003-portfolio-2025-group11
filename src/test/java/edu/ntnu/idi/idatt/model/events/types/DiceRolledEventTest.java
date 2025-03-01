package edu.ntnu.idi.idatt.model.events.types;

import edu.ntnu.idi.idatt.model.entities.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the DiceRolledEvent class.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class DiceRolledEventTest {
    /**
     * Test that the constructor and getters work as expected
     * for the DiceRolledEvent class.
     *
     * <p>Expected: The player and roll are set correctly.</p>
     */
    @Test
    public void testDiceRolledEvent() {
        Player player = new Player("Player A");
        int roll = 5;
        DiceRolledEvent diceRolledEvent = new DiceRolledEvent(player, roll);
        assertEquals(player, diceRolledEvent.getPlayer());
        assertEquals(roll, diceRolledEvent.getRoll());
    }
}
