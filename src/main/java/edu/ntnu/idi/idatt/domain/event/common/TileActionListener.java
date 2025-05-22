package edu.ntnu.idi.idatt.domain.event.common;

/**
 * Listener interface for handling {@link TileActionEvent}s.
 *
 * <p>Implementations will be notified when a player lands on a tile
 * and an associated action must be executed.</p>
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface TileActionListener {
  /**
   * Called when a tile action event occurs.
   *
   * @param e is the {@link TileActionEvent}.
   */
  void onTileAction(TileActionEvent e);
}
