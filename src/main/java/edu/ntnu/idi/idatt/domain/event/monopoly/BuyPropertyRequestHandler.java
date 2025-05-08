package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.event.EventHandler;

public class BuyPropertyRequestHandler implements EventHandler<BuyPropertyRequestEvent> {
  private final MonopolyEventListener listener;

  public BuyPropertyRequestHandler(MonopolyEventListener listener) {
    this.listener = listener;
  }

  @Override
  public void handle(BuyPropertyRequestEvent event) {
    if (event.getProperty().getCost() <= event.getAccount().getBalance()) {
      listener.onBuyPropertyRequest(event.getPlayer(), event.getProperty(), event.getAccount());
    } else {
      listener.onInsufficientFunds(event.getPlayer(), event.getProperty());
    }
  }
}
