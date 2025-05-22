package edu.ntnu.idi.idatt.domain.entity.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.domain.entity.Tile;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link PropertyRegistry} class.
 *
 * <p>Tests the core functionality of the {@link PropertyRegistry} class, including
 * registering properties and retrieving properties at specific tiles.
 * Tests with both valid and invalid inputs.</p>
 *
 * @author Gilianne Reyes
 * @version 0.1
 */
public class PropertyRegistryTest {
  private PropertyRegistry propertyRegistry;
  private Property property;
  private Tile tile;

  /**
   * Sets up the test environment by creating a new PropertyRegistry instance,
   * Property instance and Tile instance before each test.
   */
  @BeforeEach
  public void setUp() {
    propertyRegistry = new PropertyRegistry();
    property = new Property("Park Lane", 350, 50);
    tile = new Tile(1, 1, 1);
  }

  // -------------------- Positive tests --------------------
  /**
   * Tests the registration of a valid property.
   *
   * <p>Expected outcome: The property is registered successfully
   * in the registry and can be retrieved by the tile.</p>
   */
  @Test
  void tesRegisterValidProperty() {
    propertyRegistry.registerProperty(tile.getTileId(), property);
    Optional<Property> propertyOpt = propertyRegistry.getPropertyAt(tile);
    assertTrue(propertyOpt.isPresent());
    assertEquals(propertyOpt.get(), property);
  }

  /**
   * Tests the retrieval of a non-existent property at a valid tile.
   *
   * <p>Expected outcome: The method returns an empty Optional, indicating
   * that no property is registered at the specified tile.</p>
   */
  @Test
  void testGetEmptyPropertyAtValidTile() {
    Optional<Property> propertyOpt = propertyRegistry.getPropertyAt(tile);
    assertTrue(propertyOpt.isEmpty());
  }

  // -------------------- Negative tests --------------------

  /**
   * Tests the registration of a null property at a valid tile id.
   *
   * <p>Expected outcome: IllegalArgumentException is thrown as
   * the property is null.</p>
   */
  @Test
  void testRegisterNullProperty() {
    assertThrows(IllegalArgumentException.class, () ->
        propertyRegistry.registerProperty(tile.getTileId(), null)
    );
  }

  /**
   * Tests the registration of a property at a tile that is already
   * occupied by another property.
   *
   * <p>Expected outcome: IllegalStateException is thrown.</p>
   */
  @Test
  void testRegisterPropertyAtOccupiedTile() {
    propertyRegistry.registerProperty(tile.getTileId(), property);
    Property newProperty = new Property("Mayfair", 400, 60);
    assertThrows(IllegalStateException.class, () ->
        propertyRegistry.registerProperty(tile.getTileId(), newProperty)
    );
  }

  /**
   * Tests the retrieval of a property at a null tile.
   *
   * <p>Expected outcome: IllegalArgumentException is thrown.</p>
   */
  @Test
  void testGetPropertyAtNullTile() {
    assertThrows(IllegalArgumentException.class, () ->
        propertyRegistry.getPropertyAt(null)
    );
  }
}
