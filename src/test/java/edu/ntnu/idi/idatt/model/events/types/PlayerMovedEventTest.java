package edu.ntnu.idi.idatt.model.events.types;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the PlayerMovedEvent class.
 *
 * @version 0.2
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
        Tile fromTile = new Tile(1,0,0);
        Tile toTile= new Tile(2,1,0);
        PlayerMovedEvent event = new PlayerMovedEvent(player, fromTile, toTile);
        assertEquals(player, event.getPlayer());
        assertEquals(fromTile, event.getFromTile());
        assertEquals(toTile, event.getDestinationTile());
    }
}
