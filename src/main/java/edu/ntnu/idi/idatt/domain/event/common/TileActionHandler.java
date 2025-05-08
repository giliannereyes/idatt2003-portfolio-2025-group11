package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.event.EventHandler;

/**
 * Handles the event when a player lands on a tile with an action.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class TileActionHandler implements EventHandler<TileActionEvent> {
    private final GameEventListener listener;

    public TileActionHandler(GameEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void handle(TileActionEvent event) {
        event.getTile()
              .getLandAction()
              .ifPresent(action ->
                    listener.onTileAction(event.getPlayer(), action)
              );
    }
}
