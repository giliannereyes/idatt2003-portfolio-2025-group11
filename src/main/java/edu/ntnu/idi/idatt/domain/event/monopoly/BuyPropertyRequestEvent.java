package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.monopoly.AssetsAccount;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

public class BuyPropertyRequestEvent implements GameEvent {
  private final Player player;
  private final Property property;
  private final AssetsAccount account;

  public BuyPropertyRequestEvent(Player player, Property property, AssetsAccount account) {
    this.player   = player;
    this.property = property;
    this.account  = account;
  }

  public Player getPlayer()   { return player; }
  public Property       getProperty() { return property; }
  public AssetsAccount getAccount() { return account; }
}

