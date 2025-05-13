package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.entity.Player;
import edu.ntnu.idi.idatt.domain.event.GameEvent;

public record PlayerPaidRentEvent(
      Player tenant, Player landlord, double rent,
      double tenantBalance, double landlordBalance
) implements GameEvent {}
