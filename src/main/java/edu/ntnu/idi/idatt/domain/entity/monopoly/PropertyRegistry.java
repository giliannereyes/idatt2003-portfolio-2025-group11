package edu.ntnu.idi.idatt.domain.entity.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PropertyRegistry {
  private static PropertyRegistry instance;
  private final Map<Integer, Property> properties = new HashMap<>();

  private PropertyRegistry() {
  }

  public static synchronized PropertyRegistry getInstance() {
    if (instance == null) {
      instance = new PropertyRegistry();
    }
    return instance;
  }

  /**
   * Registers a property for a specific tile ID
   */
  public void registerProperty(int tileId, Property property) {
    properties.put(tileId, property);
  }

  /**
   * Gets a property for a specific tile
   */
  public Optional<Property> getPropertyAt(Tile tile) {
    return Optional.ofNullable(properties.get(tile.getTileId()));
  }

  /**
   * Gets all registered properties
   */
  public List<Property> getAllProperties() {
    return new ArrayList<>(properties.values());
  }

  /**
   * Gets all properties owned by a specific player
   */
  public List<Property> getPropertiesByOwner(Player player) {
    return properties.values().stream()
          .filter(p -> p.getOwner().isPresent() && p.getOwner().get().equals(player))
          .collect(Collectors.toList());
  }

  /**
   * Clear all properties (useful for testing or resetting)
   */
  public void clear() {
    properties.clear();
  }
}
