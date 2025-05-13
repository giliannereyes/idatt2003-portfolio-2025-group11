package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.event.EventBus;
import edu.ntnu.idi.idatt.domain.event.EventHandler;
import edu.ntnu.idi.idatt.domain.event.GameEvent;
import edu.ntnu.idi.idatt.utils.Validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the event bus.
 *
 * @author Gilianne Reyes
 * @version 0.1
 * @since 0.1
 */
public class DefaultEventBus implements EventBus {
    private final Map<Class<? extends GameEvent>, List<EventHandler<? extends GameEvent>>> handlerMap;

    /**
     * Creates a new event bus.
     */
    public DefaultEventBus() {
        this.handlerMap = new HashMap<>();
    }

    /**
     * Registers an event handler to the event bus.
     *
     * @param eventType is the type of event to register the handler for.
     * @param handler is the event handler to register.
     * @param <E> is the type of event.
     *
     * @throws IllegalArgumentException if the event type or handler is null.
     */
    @Override
    public <E extends GameEvent> void register(Class<E> eventType, EventHandler<E> handler) {
        Validation.validateNonNull(eventType, "Event type");
        Validation.validateNonNull(handler, "Handler");
        handlerMap
                .computeIfAbsent(eventType, k -> new ArrayList<>())
                .add(handler);
    }

    /**
     * Unregisters an event handler from the event bus.
     *
     * @param eventType is the type of event to unregister the handler for.
     * @param handler is the event handler to unregister.
     * @param <E> is the type of event.
     *
     * @throws IllegalArgumentException if the event type or handler is null.
     */
    @Override
    public <E extends GameEvent> void unregister(Class<E> eventType, EventHandler<E> handler) {
        Validation.validateNonNull(eventType, "Event type");
        Validation.validateNonNull(handler, "Handler");
        List<EventHandler<? extends GameEvent>> handlers = handlerMap.get(eventType);
        if (handlers != null) {
            handlers.remove(handler);
            if (handlers.isEmpty()) {
                handlerMap.remove(eventType);
            }
        }
    }

    /**
     * Publishes an event to the event bus.
     *
     * @param event is the event to publish.
     *
     * @throws IllegalArgumentException if the event is null.
     */
    @Override
    public void publish(GameEvent event) {
        Validation.validateNonNull(event, "Event");
        Class<? extends GameEvent> eventClass = event.getClass();
        List<EventHandler<? extends GameEvent>> handlers = handlerMap.get(eventClass);
        if (handlers != null) {
            for (EventHandler<? extends GameEvent> rawHandler : handlers) {
                @SuppressWarnings("unchecked")
                EventHandler<GameEvent> handler = (EventHandler<GameEvent>) rawHandler;
                handler.handle(event);
            }
        }
    }
}
