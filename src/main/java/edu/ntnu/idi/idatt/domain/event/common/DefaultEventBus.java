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
 * Default implementation of {@link EventBus}.
 *
 * @author Gilianne Reyes
 * @version 0.1
 * @since 0.1
 */
public class DefaultEventBus implements EventBus {
  private final Map<Class<? extends GameEvent>, List<EventHandler<? extends GameEvent>>> handlerMap;

  /**
   * Constructs a new, empty event bus.
   */
  public DefaultEventBus() {
    this.handlerMap = new HashMap<>();
  }

  /**
   * Registers an event handler for a given event type.
   *
   * @param eventType is the type of event to register the handler for.
   * @param handler is the handler to invoke when events of this type are published.
   * @param <E> is the concrete {@link GameEvent} subtype.
   *
   * @throws IllegalArgumentException if the {@code event type} or {@code event handler} is null.
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
   * @param eventType is the event class to unsubscribe from.
   * @param handler is the event handler to unregister.
   * @param <E> is the concrete {@link GameEvent} subtype.
   *
   * @throws IllegalArgumentException if the {@code event type} or {@code event handler} is null.
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
   * Publishes an event to all registered handlers of its exact runtime type.
   *
   * @param event is the event to publish.
   *
   * @throws IllegalArgumentException if the {@code event} is null.
   */
  @Override
  public void publish(GameEvent event) {
    Validation.validateNonNull(event, "Event");
    Class<? extends GameEvent> eventClass = event.getClass();
    List<EventHandler<? extends GameEvent>> handlers = handlerMap.get(eventClass);
    if (handlers != null) {
      handlers.forEach(rawHandler -> {
        @SuppressWarnings("unchecked")
        EventHandler<GameEvent> handler = (EventHandler<GameEvent>) rawHandler;
        handler.handle(event);
      });

    }
  }
}
