package edu.ntnu.idi.idatt.domain.event.types;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.common.DiceRolledEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the DiceRolledEvent class.
 *
 * @version 0.2
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
        int roll1 = 5;
        int roll2 = 3;
        DiceRolledEvent diceRolledEvent = new DiceRolledEvent(player, roll1, roll2);
        assertEquals(player, diceRolledEvent.player());
        assertEquals(roll1, diceRolledEvent.roll1());
        assertEquals(roll2, diceRolledEvent.roll2());
    }
}
