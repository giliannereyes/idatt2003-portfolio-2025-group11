package edu.ntnu.idi.idatt.domain.entity.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import edu.ntnu.idi.idatt.utils.Validation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Registry for mapping board tiles to Monopoly {@link Property Properties}.
 *
 * <p>Allows registering new properties by tile ID, querying by tile or owner,
 * and retrieving or clearing the full set.</p>
 *
 * @author Gilianne Reyes
 * @version 0.1
 */
public class PropertyRegistry {
  private final Map<Integer, Property> properties = new HashMap<>();

  /**
   * Associates a {@link Property} with a board tile.
   *
   * @param tileId is the ID of the tile where the property is located.
   * @param property is the property to register.
   *
   * @throws IllegalArgumentException if {@code property} is null.
   * @throws IllegalStateException if a property is already registered for the given tile ID.
   */
  public void registerProperty(int tileId, Property property) {
    Validation.validateNonNull(property, "Property");
    if (properties.containsKey(tileId)) {
      throw new IllegalStateException("Property already registered for tile ID: " + tileId);
    }
    properties.put(tileId, property);
  }

  /**
   * Retrieves the property located at the given tile.
   *
   * @param tile is the tile a property may be registered to.
   * @return an {@link Optional} containing the property, or empty if none registered.
   *
   * @throws IllegalArgumentException if {@code tile} is null.
   */
  public Optional<Property> getPropertyAt(Tile tile) {
    Validation.validateNonNull(tile, "Tile");
    return Optional.ofNullable(properties.get(tile.getTileId()));
  }

  /**
   * Returns a list of all properties currently registered in this registry.
   *
   * @return a new {@link List} containing every property in this registry
   */
  public List<Property> getAllProperties() {
    return new ArrayList<>(properties.values());
  }

  /**
   * Finds all properties currently owned by the given player.
   *
   * @param player the non-null owner to filter by
   * @return a list of properties whose {@link Property#getOwner() owner}
   *         matches the given player
   * @throws IllegalArgumentException if {@code player} is null
   */
  public List<Property> getPropertiesByOwner(Player player) {
    return properties.values().stream()
          .filter(p -> p.getOwner().isPresent() && p.getOwner().get().equals(player))
          .collect(Collectors.toList());
  }

  /**
   * Removes all property registrations from this registry.
   *
   * <p>Useful for resetting game state or testing.</p>
   */
  public void clear() {
    properties.clear();
  }
}
