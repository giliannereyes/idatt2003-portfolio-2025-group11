package edu.ntnu.idi.idatt.domain.event.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idi.idatt.domain.entity.Player;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link PlayerPaidRentEvent} record.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class PlayerPaidRentEventTest {

  /**
   * Tests that the constructor and accessors work as expected
   * for the PlayerPaidRentEvent record.
   *
   * <p>Expected: The tenant, landlord, rent amount, and balances
   * are set and returned correctly.</p>
   */
  @Test
  public void testPlayerPaidRentEvent() {
    Player tenant = new Player("Eve");
    Player landlord = new Player("Frank");
    double rent = 50.0;
    double tenantBalance = 950.0;
    double landlordBalance = 1050.0;

    PlayerPaidRentEvent event = new PlayerPaidRentEvent(
          tenant, landlord, rent, tenantBalance, landlordBalance
    );

    assertEquals(tenant,          event.tenant());
    assertEquals(landlord,        event.landlord());
    assertEquals(rent,            event.rent());
    assertEquals(tenantBalance,   event.tenantBalance());
    assertEquals(landlordBalance, event.landlordBalance());
  }
}
