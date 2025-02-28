package edu.ntnu.idi.idatt.model.events;

/**
 * Interface for game event listeners.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface GameEventListener {
    /**
     * Handles a game event.
     *
     * @param event is the game event to handle.
     */
    void handleGameEvent(GameEvent event);
}
