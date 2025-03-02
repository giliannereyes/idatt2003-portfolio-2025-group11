package edu.ntnu.idi.idatt.model.actions;

import edu.ntnu.idi.idatt.model.entities.Player;

/**
 * Interface for actions that can be performed on a tile.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface TileAction {
  void perform(Player player);
}
