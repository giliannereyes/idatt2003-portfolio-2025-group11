package edu.ntnu.idi.idatt.domain.event.common;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

public class PlayerWonEvent implements GameEvent {
  private final Player winner;

  public PlayerWonEvent(Player winner) {
    this.winner = winner;
  }

  public Player getWinner() {
    return winner;
  }
}
