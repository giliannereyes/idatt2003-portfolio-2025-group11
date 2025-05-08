package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.event.EventHandler;

public class PlayerBankruptHandler implements EventHandler<PlayerBankruptEvent> {
  private final MonopolyEventListener listener;

  public PlayerBankruptHandler(MonopolyEventListener listener) {
    this.listener = listener;
  }

  @Override
  public void handle(PlayerBankruptEvent event) {
    listener.onPlayerBankrupt(event.getPlayer());
  }
}
