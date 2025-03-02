package edu.ntnu.idi.idatt.model.events.types;

import edu.ntnu.idi.idatt.model.entities.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the PlayerMovedEvent class.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class PlayerMovedEventTest {
    /**
     * Tests that the constructor and getters work as expected
     * for the PlayerMovedEvent class.
     *
     * <p>Expected: The player, fromTileId and toTileId are set correctly.</p>
     */
    @Test
    public void testPlayerMovedEvent() {
        Player player = new Player("Player A");
        int fromTileId = 10;
        int toTileId = 15;
        PlayerMovedEvent event = new PlayerMovedEvent(player, fromTileId, toTileId);
        assertEquals(player, event.getPlayer());
        assertEquals(fromTileId, event.getFromTileId());
        assertEquals(toTileId, event.getToTileId());
    }
}
