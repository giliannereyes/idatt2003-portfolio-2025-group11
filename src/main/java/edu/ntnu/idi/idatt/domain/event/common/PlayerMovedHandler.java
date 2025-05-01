package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.event.EventHandler;
import edu.ntnu.idi.idatt.ui.controller.GameEventListener;
import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.Tile;

/**
 * Handles a PlayerMovedEvent.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class PlayerMovedHandler implements EventHandler<PlayerMovedEvent> {
    private final GameEventListener listener;

    /**
     * Constructs a PlayerMovedHandler instance.
     *
     */
    public PlayerMovedHandler(GameEventListener listener) {
        this.listener = listener;
    }

    /**
     * Handles a PlayerMovedEvent.
     *
     * @param event is the PlayerMovedEvent to handle.
     */
    @Override
    public void handle(PlayerMovedEvent event) {
        Player player = event.getPlayer();
        Tile fromTile = event.getFromTile();
        Tile destinationTile = event.getDestinationTile();
        listener.onPlayerMoved(player, fromTile, destinationTile);
    }
}
