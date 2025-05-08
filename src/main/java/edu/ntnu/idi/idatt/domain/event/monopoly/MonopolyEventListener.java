package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.entity.monopoly.AssetsAccount;
import edu.ntnu.idi.idatt.domain.entity.monopoly.Property;

public interface MonopolyEventListener {
  void onBuyPropertyRequest(Player player, Property property, AssetsAccount account);
  void onInsufficientFunds(Player player, Property property);
  void onRentPaid(Player tenant, Player landlord, double rent, double tenantBalance, double landlordBalance);
  void onPlayerBankrupt(Player player);
}
