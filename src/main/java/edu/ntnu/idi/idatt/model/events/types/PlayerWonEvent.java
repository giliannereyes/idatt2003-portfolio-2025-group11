package edu.ntnu.idi.idatt.model.events.types;

import edu.ntnu.idi.idatt.model.entities.Player;

public class PlayerWonEvent implements GameEvent {
  private final Player winner;

  public PlayerWonEvent(Player winner) {
    this.winner = winner;
  }

  public Player getWinner() {
    return winner;
  }
}
