package edu.ntnu.idi.idatt.domain.entity.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.domain.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link Property}.
 *
 * <p>Tests cover property initialization and ownership management
 * with both positive and negative test cases.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class PropertyTest {
  private String name;
  private double cost;
  private double rent;
  private Player owner;
  private Property property;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    name = "TestProperty";
    cost = 200;
    rent = 50;
    owner = new Player("TestPlayer");
    property = new Property(name, cost, rent);
  }

  // ---------- Positive tests ----------
  /**
   * Tests if the Property is initialized correctly with valid parameters.
   *
   * <p>Expected: The property should be initialized with the
   * correct name, cost, and rent. The property should not be owned by default.</p>
   */
  @Test
  void testValidInitialization() {
    assertNotNull(property);
    assertEquals(name, property.getName());
    assertEquals(cost, property.getCost());
    assertEquals(rent, property.getRent());
    assertFalse(property.isOwned());
  }

  /**
   * Tests if the property can be owned by a valid player.
   *
   * <p>Expected: The property should be owned by the player
   * and the owner should be set correctly.</p>
   */
  @Test
  void testSetValidOwner() {
    property.setOwner(owner);
    assertTrue(property.isOwned());
    assertTrue(property.getOwner().isPresent());
    assertEquals(owner, property.getOwner().get());
  }

  /**
   * Tests if the property can be reset to not owned.
   *
   * <p>Expected: The property should not be owned after reset
   * and the owner should not be present.</p>
   */
  @Test
  void testResetOwnership() {
    property.setOwner(owner);
    assertTrue(property.isOwned());
    property.resetOwnership();
    assertFalse(property.isOwned());
    assertFalse(property.getOwner().isPresent());
  }

  // ---------- Negative tests ----------
  /**
   * Tests if the Property constructor throws an exception when
   * initialized with invalid parameters.
   *
   * <p>Expected: The constructor should throw an IllegalArgumentException
   * for invalid name, cost, or rent.</p>
   */
  @Test
  void testInvalidInitialization() {
    assertThrows(IllegalArgumentException.class, () -> new Property("", cost, rent));
    assertThrows(IllegalArgumentException.class, () -> new Property(null, cost, rent));
    assertThrows(IllegalArgumentException.class, () -> new Property(name, -1, rent));
    assertThrows(IllegalArgumentException.class, () -> new Property(name, cost, -1));
  }

  /**
   * Tests if the setOwner method throws an exception when
   * attempting to set a null owner.
   *
   * <p>Expected: The method should throw an IllegalArgumentException.</p>
   */
  @Test
  void testSetOwnerWithNull() {
    assertThrows(IllegalArgumentException.class, () -> property.setOwner(null));
  }
}
