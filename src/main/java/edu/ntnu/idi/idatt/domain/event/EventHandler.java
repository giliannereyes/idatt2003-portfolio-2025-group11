package edu.ntnu.idi.idatt.domain.event;

/**
 * Interface for event handlers.
 *
 * @param <E> is the type of event to handle.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public interface EventHandler<E extends GameEvent> {
  /**
   * Handles an event.
   *
   * @param event is the event to handle.
   */
  void handle(E event);
}
