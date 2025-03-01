package edu.ntnu.idi.idatt.model.events.bus;

import edu.ntnu.idi.idatt.model.events.handlers.EventHandler;
import edu.ntnu.idi.idatt.model.events.types.GameEvent;

/**
 * Interface for the event bus.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface EventBus {
    /**
     * Registers an event handler.
     *
     * @param eventType is the type of event to register the handler for.
     * @param handler is the event handler to register.
     */
    <E extends GameEvent> void register(Class<E> eventType, EventHandler<E> handler);

    /**
     * Unregisters an event handler.
     *
     * @param eventType is the type of event to unregister the handler for.
     * @param handler is the event handler to unregister.
     */
    <E extends GameEvent> void unregister(Class<E> eventType, EventHandler<E> handler);

    /**
     * Publishes an event to all registered event handlers.
     *
     * @param event is the event to publish.
     */
    void publish(GameEvent event);
}
