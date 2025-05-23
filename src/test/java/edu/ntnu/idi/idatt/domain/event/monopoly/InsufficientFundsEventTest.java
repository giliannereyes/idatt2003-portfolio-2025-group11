package edu.ntnu.idi.idatt.domain.event.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link InsufficientFundsEvent} record.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class InsufficientFundsEventTest {

  /**
   * Tests that the constructor and accessors work as expected
   * for the InsufficientFundsEvent record.
   *
   * <p>Expected: The player and property fields are set and
   * returned correctly.</p>
   */
  @Test
  public void testInsufficientFundsEvent() {
    Player player = new Player("Charlie");
    Property property = new Property("Boardwalk", 400, 40);

    InsufficientFundsEvent event = new InsufficientFundsEvent(player, property);

    assertEquals(player,   event.player());
    assertEquals(property, event.property());
  }
}
