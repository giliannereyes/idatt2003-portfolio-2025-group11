package edu.ntnu.idi.idatt.domain.event.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idi.idatt.domain.entity.Player;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link PlayerWonEvent} record.
 *
 * @version 0.2
 * @since 0.1
 * @author Gilianne Reyes
 */
public class PlayerWonEventTest {
  /**
   * Tests that the constructor and accessor work as expected
   * for the PlayerWonEvent record.
   *
   * <p>Expected: The winner field is set and returned correctly.</p>
   */
  @Test
  public void testPlayerWonEvent() {
    Player player = new Player("Alice");
    PlayerWonEvent event = new PlayerWonEvent(player);

    assertEquals(player, event.winner());
  }
}
