package edu.ntnu.idi.idatt.domain.event.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idi.idatt.domain.entity.Player;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link PlayerBankruptEvent} record.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class PlayerBankruptEventTest {
  /**
   * Tests that the constructor and accessor work as expected
   * for the PlayerBankruptEvent record.
   *
   * <p>Expected: The player field is set and returned correctly.</p>
   */
  @Test
  public void testPlayerBankruptEvent() {
    Player player = new Player("Dana");
    PlayerBankruptEvent event = new PlayerBankruptEvent(player);

    assertEquals(player, event.player());
  }
}
