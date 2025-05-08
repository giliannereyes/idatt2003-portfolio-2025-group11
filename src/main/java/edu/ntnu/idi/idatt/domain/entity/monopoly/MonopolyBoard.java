package edu.ntnu.idi.idatt.domain.entity.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Board;
import edu.ntnu.idi.idatt.domain.entity.Tile;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A Monopoly-specific Board that knows which tiles are properties.
 */
public class MonopolyBoard extends Board {
  private final Map<Integer, Property> propertyMap = new HashMap<>();

  public MonopolyBoard(int rows, int columns) {
    super(rows, columns);
  }

  /**
   * Register a property at the given tile ID.
   */
  public void addMonopolyProperty(int tileId, Property property) {
    propertyMap.put(tileId, property);
  }

  /**
   * Lookup a property by tile.
   */
  public Optional<Property> getPropertyFor(Tile tile) {
    return Optional.ofNullable(propertyMap.get(tile.getTileId()));
  }
}

