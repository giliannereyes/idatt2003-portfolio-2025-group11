package edu.ntnu.idi.idatt.domain.event.monopoly;

import edu.ntnu.idi.idatt.domain.event.EventHandler;

public class PlayerPaidRentHandler implements EventHandler<PlayerPaidRentEvent> {
    private final MonopolyEventListener listener;

    public PlayerPaidRentHandler(MonopolyEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void handle(PlayerPaidRentEvent event) {
        listener.onRentPaid(
              event.getTenant(),
              event.getLandlord(),
              event.getRent(),
              event.getTenantBalance(),
              event.getLandlordBalance()
        );
    }
}
