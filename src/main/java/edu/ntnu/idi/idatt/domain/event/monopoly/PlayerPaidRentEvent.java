package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

public class PlayerPaidRentEvent implements GameEvent {
  private final Player tenant;
  private final Player landlord;
  private final double rent;
  private final double tenantBalance;
  private final double landlordBalance;

  public PlayerPaidRentEvent(Player tenant, Player landlord, double rent, double tenantBalance, double landlordBalance) {
    this.tenant = tenant;
    this.landlord = landlord;
    this.rent = rent;
    this.tenantBalance = tenantBalance;
    this.landlordBalance = landlordBalance;
  }

  public Player getTenant() { return tenant; }
  public Player getLandlord() { return landlord; }
  public double getRent() { return rent; }
  public double getTenantBalance() { return tenantBalance; }
  public double getLandlordBalance() { return landlordBalance; }
}
