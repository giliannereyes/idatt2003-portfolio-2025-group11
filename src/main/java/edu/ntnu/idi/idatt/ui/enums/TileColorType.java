package edu.ntnu.idi.idatt.ui.enums;

import javafx.scene.paint.Color;

/**
 * Represents the color types used for different tiles in the
 * Ladders game.
 * Each tile color type has a corresponding color value.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public enum TileColorType {
  LADDER_START(Color.GREEN),
  LADDER_END(Color.LIGHTGREEN),
  SNAKE_START(Color.web("#982020")),
  SNAKE_END(Color.web("#f73333")),
  RESET(Color.YELLOW),
  SKIP_TURN(Color.VIOLET),
  DEFAULT_DARK(Color.LIGHTGREY),
  DEFAULT_LIGHT(Color.web("#F0F0F0"));

  private final Color color;

  /**
   * Constructs a TileColorType instance.
   *
   * @param color is the color associated with the tile type.
   */
  TileColorType(Color color) {
    this.color = color;
  }

  /**
   * Retrieves the color associated with the tile type.
   *
   * @return the color of the tile type.
   */
  public Color getColor() {
    return color;
  }
}
