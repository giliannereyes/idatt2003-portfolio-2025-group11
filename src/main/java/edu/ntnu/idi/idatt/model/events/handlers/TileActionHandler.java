package edu.ntnu.idi.idatt.model.events.handlers;

import edu.ntnu.idi.idatt.controller.GameEventListener;
import edu.ntnu.idi.idatt.model.actions.TileAction;
import edu.ntnu.idi.idatt.model.entities.Tile;
import edu.ntnu.idi.idatt.model.events.types.TileActionEvent;

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
        Tile tile = event.getTile();
        if (tile.getLandAction().isPresent()) {
            TileAction landAction = tile.getLandAction().get();
            listener.onTileAction(event.getPlayer(), landAction);
        }
    }
}
