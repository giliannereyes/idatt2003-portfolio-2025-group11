package edu.ntnu.idi.idatt.domain.event.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.monopoly.AssetsAccount;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link BuyPropertyRequestEvent} record.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class BuyPropertyRequestEventTest {
  /**
   * Tests that the constructor and accessors work as expected
   * for the BuyPropertyRequestEvent record.
   *
   * <p>Expected: The player, property, and account fields are set and
   * returned correctly.</p>
   */
  @Test
  public void testBuyPropertyRequestEvent() {
    Player player = new Player("Bob");
    Property property = new Property("Park Place", 200, 20);
    AssetsAccount account = new AssetsAccount(player, 1000);
    BuyPropertyRequestEvent event = new BuyPropertyRequestEvent(player, property, account);

    assertEquals(player,   event.player());
    assertEquals(property, event.property());
    assertEquals(account,  event.account());
  }
}
