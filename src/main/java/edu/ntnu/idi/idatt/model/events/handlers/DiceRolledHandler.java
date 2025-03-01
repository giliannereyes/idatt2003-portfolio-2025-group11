package edu.ntnu.idi.idatt.model.events.handlers;

import edu.ntnu.idi.idatt.model.events.types.DiceRolledEvent;

public class DiceRolledHandler implements EventHandler<DiceRolledEvent> {
    @Override
    public void handle(DiceRolledEvent event) {
        System.out.println("[PLAYER " + event.getPlayer().getName() + "] rolled a " + event.getRoll());
    }
}
