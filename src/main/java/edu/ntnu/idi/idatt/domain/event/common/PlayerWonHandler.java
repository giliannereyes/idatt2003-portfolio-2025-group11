package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.event.EventHandler;

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
