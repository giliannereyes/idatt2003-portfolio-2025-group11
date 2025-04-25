package edu.ntnu.idi.idatt.model.events.handlers;

import edu.ntnu.idi.idatt.controller.GameEventListener;
import edu.ntnu.idi.idatt.model.events.types.PlayerWonEvent;

public class PlayerWonHandler implements EventHandler<PlayerWonEvent> {
  private final GameEventListener listener;

  /**
   * Constructs a PlayerMovedHandler instance.
   *
   */
  public PlayerWonHandler(GameEventListener listener) {
    this.listener = listener;
  }

  @Override
  public void handle(PlayerWonEvent event) {
    listener.onPlayerWon(event.getWinner());
  }
}
