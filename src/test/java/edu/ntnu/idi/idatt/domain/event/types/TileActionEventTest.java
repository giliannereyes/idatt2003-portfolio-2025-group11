package edu.ntnu.idi.idatt.domain.event.types;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.domain.event.common.TileActionEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the TileActionEvent class.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class TileActionEventTest {
    /**
     * Tests that the constructor and getters work as expected
     * for the TileActionEvent class.
     *
     * <p>Expected: The player and tile are set correctly.</p>
     */
    @Test
    public void testTileActionEvent() {
        Player player = new Player("Player A");
        Tile tile = new Tile(1, 0, 0);
        TileActionEvent event = new TileActionEvent(player, tile);
        assertEquals(player, event.getPlayer());
        assertEquals(tile, event.getTile());
    }
}
