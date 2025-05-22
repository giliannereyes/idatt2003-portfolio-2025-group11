package edu.ntnu.idi.idatt.domain.event.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link TileActionEvent} record.
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
    assertEquals(player, event.player());
    assertEquals(tile, event.tile());
  }
}
