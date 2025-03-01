package edu.ntnu.idi.idatt.model.events.handlers;

import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.events.types.PlayerMovedEvent;

/**
 * Handles a PlayerMovedEvent.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class PlayerMovedHandler implements EventHandler<PlayerMovedEvent> {
    /**
     * Handles a PlayerMovedEvent.
     *
     * @param event is the PlayerMovedEvent to handle.
     */
    @Override
    public void handle(PlayerMovedEvent event) {
        Player player = event.getPlayer();
        int fromTileId = event.getFromTileId();
        int toTileId = event.getToTileId();
        System.out.println("[PLAYER " + player.getName() + "] moved from tile " + fromTileId + " to tile " + toTileId);
    }
}
