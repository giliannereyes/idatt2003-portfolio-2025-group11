package edu.ntnu.idi.idatt.domain.event.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.domain.event.EventHandler;
import edu.ntnu.idi.idatt.domain.event.GameEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link DefaultEventBus} class.
 *
 * @version 0.1
 * @since 0.1
 * @author Gilianne Reyes
 */
public class DefaultEventBusTest {
  private DefaultEventBus eventBus;
  private TestEventHandler handler;
  private TestEvent event;

  /**
   * Sets up a new event bus, handler and event before each test.
   */
  @BeforeEach
  public void setUp() {
    eventBus = new DefaultEventBus();
    handler = new TestEventHandler();
    event = new TestEvent();
  }

  // ----------- Positive Tests -----------

  /**
   * Tests that an event handler can be registered and an event can be published.
   *
   * <p>Expected: The event is published through the event bus and the
   * handler handles the event.</p>
   */
  @Test
  public void testRegisterAndPublishEvent() {
    eventBus.register(TestEvent.class, handler);
    eventBus.publish(event);
    assertTrue(handler.isCalled());
  }

  /**
   * Tests that an event handler can be unregistered.
   *
   * <p>Expected: The only handler for the event is unregistered
   * and the event should not be handled consequently.</p>
   */
  @Test
  public void testUnregisterHandler() {
    eventBus.register(TestEvent.class, handler);
    eventBus.unregister(TestEvent.class, handler);
    eventBus.publish(event);
    assertFalse(handler.isCalled());
  }

  // ----------- Negative Tests -----------

  /**
   * Tests that an event handler cannot be registered if the event type is null.
   *
   * <p>Expected: An IllegalArgumentException is thrown.</p>
   */
  @Test
  public void testRegisterNullEventType() {
    assertThrows(IllegalArgumentException.class, () -> eventBus.register(null, handler));
  }

  /**
   * Tests that an event handler cannot be registered if the handler is null.
   *
   * <p>Expected: An IllegalArgumentException is thrown.</p>
   */
  @Test
  public void testRegisterNullHandler() {
    assertThrows(IllegalArgumentException.class, () -> eventBus.register(TestEvent.class, null));
  }

  /**
   * Tests that an event handler cannot be unregistered if the event type is null.
   *
   * <p>Expected: An IllegalArgumentException is thrown.</p>
   */
  @Test
  public void testUnregisterNullEventType() {
    assertThrows(IllegalArgumentException.class, () -> eventBus.unregister(null, handler));
  }

  /**
   * Tests that an event handler cannot be unregistered if the handler is null.
   *
   * <p>Expected: An IllegalArgumentException is thrown.</p>
   */
  @Test
  public void testUnregisterNullHandler() {
    assertThrows(IllegalArgumentException.class, () -> eventBus.unregister(TestEvent.class, null));
  }

  /**
   * Tests that an event cannot be published if the event is null.
   *
   * <p>Expected: An IllegalArgumentException is thrown.</p>
   */
  @Test
  public void testPublishNullEvent() {
    assertThrows(IllegalArgumentException.class, () -> eventBus.publish(null));
  }

  // ----------- Dummy classes for testing -----------
  /**
   * A dummy implementation of GameEvent for testing purposes.
   */
  private static class TestEvent implements GameEvent {}

  /**
   * A dummy implementation of EventHandler for testing purposes.
   */
  private static class TestEventHandler implements EventHandler<TestEvent> {
    private boolean called = false;

    /**
     * Handles the event. For testing purposes, it sets called to true.
     *
     * @param event is the event to handle.
     */
    @Override
    public void handle(TestEvent event) {
      called = true;
    }

    /**
     * Checks if the handler has been called.
     *
     * @return true if the handler has been called, false otherwise.
     */
    public boolean isCalled() {
      return called;
    }
  }
}


