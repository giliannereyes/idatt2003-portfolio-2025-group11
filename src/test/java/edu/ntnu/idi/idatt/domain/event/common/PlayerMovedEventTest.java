package edu.ntnu.idi.idatt.domain.event.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link PlayerMovedEvent} record.
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
    Tile fromTile = new Tile(1, 0, 0);
    Tile toTile = new Tile(2, 1, 0);
    PlayerMovedEvent event = new PlayerMovedEvent(player, fromTile, toTile);
    assertEquals(player, event.player());
    assertEquals(fromTile, event.fromTile());
    assertEquals(toTile, event.destinationTile());
  }
}
