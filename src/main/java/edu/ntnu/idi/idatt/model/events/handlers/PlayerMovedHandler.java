package edu.ntnu.idi.idatt.model.events.handlers;

import edu.ntnu.idi.idatt.controller.GameEventListener;
import edu.ntnu.idi.idatt.model.entities.Player;
import edu.ntnu.idi.idatt.model.entities.Tile;
import edu.ntnu.idi.idatt.model.events.types.PlayerMovedEvent;

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
